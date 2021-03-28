package com.example.demo.controller;

import com.example.demo.dto.CertificateInfoDto;
import com.example.demo.dto.CreateCertificateDto;
import com.example.demo.exception.AliasExistsException;
import com.example.demo.exception.CertificateAuthorityException;
import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.exception.InvalidIssuerException;
import com.example.demo.model.CertificateInfo;
import com.example.demo.service.CertificateInfoService;
import com.example.demo.service.CertificateService;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/api/certificates")
public class CertificatesController {

    private final CertificateService certificateService;
    private final CertificateInfoService certificateInfoService;

    @Autowired
    CertificatesController(CertificateService certificateService,
                           CertificateInfoService certificateInfoService) {
        this.certificateService = certificateService;
        this.certificateInfoService = certificateInfoService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCertificate(Principal principal,
                                                  @RequestBody CreateCertificateDto createCertificateDto)
            throws CertificateNotFoundException, CertificateAuthorityException, InvalidIssuerException, AliasExistsException {
        certificateService.createCertificate(createCertificateDto);
        return ResponseEntity.created(URI.create("wontneedyou")).build();
    }

    @GetMapping
    public ResponseEntity<Page<CertificateInfoDto>> getCertificates(Pageable pageable) {
        return ResponseEntity.ok(certificateInfoService.findAll(pageable));
    }

    @GetMapping(path = "/alias/{alias}")
    public ResponseEntity<CertificateInfoDto> getByAlias(@PathVariable String alias) {
        return ResponseEntity.ok(certificateInfoService.findByAliasIgnoreCase(alias));
    }

}
