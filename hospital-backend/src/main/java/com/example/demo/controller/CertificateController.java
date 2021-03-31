package com.example.demo.controller;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('ADMIN')")
public class CertificateController {

	@Autowired
	private CertificateService certificateService;

	@PostMapping
	public ResponseEntity<CertificateRequestDTO> sendRequest(@RequestBody CertificateRequestDTO certificateRequestDTO) {
		this.certificateService.sendCertificateRequest(certificateRequestDTO);
		return ResponseEntity.ok(certificateRequestDTO);
	}

}
