package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.certificate.CertificateRequestDTO;
import com.example.demo.mapper.CertificateRequestMapper;
import com.example.demo.service.CertificateInfoService;
import com.example.demo.service.CertificateRequestService;
import com.example.demo.service.CertificateValidationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/requests", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CertificateRequestController {

	private final CertificateValidationService certificateValidationService;
	private final CertificateInfoService certificateInfoService;
	private final CertificateRequestService certificateRequestService;
	private final CertificateRequestMapper certificateRequestMapper;

	@GetMapping("/{serial}")
	public ResponseEntity<Boolean> validate(@PathVariable long serial) {
		return ResponseEntity.ok(this.certificateValidationService.isCertificateValid(serial));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('REQUEST_CERTIFICATES')")
	public ResponseEntity<Void> create(@Valid @RequestBody CertificateRequestDTO requestDTO) {
		this.certificateRequestService.save(this.certificateRequestMapper.map(requestDTO));
		return ResponseEntity.ok().build();			
	}

	@DeleteMapping("/{serial}")
	@PreAuthorize("hasAuthority('REQUEST_CERTIFICATES')")
	public ResponseEntity<Void> revoke(@PathVariable long serial) {
		this.certificateInfoService.revoke(serial, "Revocation requested by hospital admin.");
		return ResponseEntity.ok().build();			
	}

}
