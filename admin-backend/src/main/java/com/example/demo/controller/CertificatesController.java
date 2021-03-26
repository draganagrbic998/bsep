package com.example.demo.controller;

import com.example.demo.dto.CreateCertificateDto;
import com.example.demo.exception.AliasExistsException;
import com.example.demo.exception.CertificateAuthorityException;
import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.exception.InvalidIssuerException;
import com.example.demo.service.CertificateService;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.security.Principal;

@RestController
@RequestMapping(value = "api/certificates")
public class CertificatesController {

    private final CertificateService certificateService;

    @Autowired
    CertificatesController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createCertificate(Principal principal,
                                                  @RequestBody CreateCertificateDto createCertificateDto)
            throws CertificateNotFoundException, CertificateAuthorityException, InvalidIssuerException, AliasExistsException {
        certificateService.createCertificate(createCertificateDto);
        return ResponseEntity.created(URI.create("wont need you")).build();
    }
}
