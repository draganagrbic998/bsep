package com.example.demo.controller;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.dto.RevokeRequestDTO;
import com.example.demo.dto.CertificateDTO;
import com.example.demo.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {

	@Autowired
	private CertificateService certificateService;

	@PostMapping
	public ResponseEntity<CertificateDTO> create(@RequestBody CertificateDTO certificateDTO) {
		this.certificateService.save(certificateDTO);
		return ResponseEntity.ok(certificateDTO);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping(value = "/request")
	public ResponseEntity<CertificateRequestDTO> request(@RequestBody CertificateRequestDTO requestDTO) {
		this.certificateService.sendRequest(requestDTO);
		return ResponseEntity.ok(requestDTO);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping
	public ResponseEntity<RevokeRequestDTO> revoke(@RequestBody RevokeRequestDTO revokeRequestDTO) {
		this.certificateService.sendRevokeRequest(revokeRequestDTO);
		return ResponseEntity.ok(revokeRequestDTO);
	}
}
