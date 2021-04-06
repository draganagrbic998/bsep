package com.example.demo.controller;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.certificate.CertificateRequestDTO;
import com.example.demo.mapper.CertificateRequestMapper;
import com.example.demo.service.CertificateRequestService;
import com.example.demo.service.CertificateValidationService;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/requests", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
@AllArgsConstructor
public class CertificateRequestController {

	private final CertificateValidationService certificateValidationService;
	private final CertificateRequestService certificateService;
	private final CertificateRequestMapper certificateMapper;
		
	@GetMapping
	public ResponseEntity<Page<CertificateRequestDTO>> findAll(Pageable pageable) {
		return ResponseEntity.ok(this.certificateService.findAll(pageable).map(CertificateRequestDTO::new));
	}

	@PreAuthorize("permitAll()")
	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody CertificateRequestDTO requestDTO, HttpServletRequest request) {
		if (!this.certificateValidationService.isCertificateValid(((X509Certificate[]) 
				request.getAttribute(Constants.CERT_ATTRIBUTE))[0].getSerialNumber().longValue())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		this.certificateService.save(this.certificateMapper.map(requestDTO));
		return ResponseEntity.ok().build();			
	}

	
}
