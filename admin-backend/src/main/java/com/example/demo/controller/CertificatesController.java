package com.example.demo.controller;

import com.example.demo.dto.BooleanDTO;
import com.example.demo.dto.CertificateInfoDTO;
import com.example.demo.dto.CreateCertificateDTO;
import com.example.demo.exception.AliasExistsException;
import com.example.demo.exception.CertificateAuthorityException;
import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.exception.InvalidIssuerException;
import com.example.demo.service.CertificateInfoService;
import com.example.demo.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "/api/certificates")
@PreAuthorize("hasAuthority('SUPER_ADMIN')")	
public class CertificatesController {

	@Autowired
	private CertificateService certificateService;

	@Autowired
	private CertificateInfoService certificateInfoService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createCertificate(@RequestBody CreateCertificateDTO createCertificateDto) throws CertificateNotFoundException,
			CertificateAuthorityException, InvalidIssuerException, AliasExistsException {
		this.certificateService.createCertificate(createCertificateDto);
		return ResponseEntity.created(URI.create("wontneedyou")).build();
	}

	@GetMapping
	public ResponseEntity<Page<CertificateInfoDTO>> getCertificates(Pageable pageable) {
		return ResponseEntity.ok(certificateInfoService.findAll(pageable));
	}

	@GetMapping(path = "/alias/{alias}")
	public ResponseEntity<CertificateInfoDTO> getByAlias(@PathVariable String alias) {
		return ResponseEntity.ok(certificateInfoService.findByAliasIgnoreCase(alias));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<BooleanDTO> revokeCertificate(@PathVariable("id") Long id) {
		this.certificateService.revokeCertificate(id);
		return ResponseEntity.ok(new BooleanDTO(true));
	}

}
