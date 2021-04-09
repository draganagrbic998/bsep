package com.example.demo.controller;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.dto.CertificateDTO;
import com.example.demo.service.CertificateService;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('ADMIN')")
@AllArgsConstructor
public class CertificateController {

	private final CertificateService certificateService;

	@PreAuthorize("permitAll()")
	@PostMapping
	public ResponseEntity<CertificateDTO> create(@RequestBody CertificateDTO certificateDTO, HttpServletRequest request) {
		if(!this.certificateService.validateCertificate(((X509Certificate[]) 
				request.getAttribute(Constants.CERTIFICATE_ATTRIBUTE))[0])) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		this.certificateService.save(certificateDTO);
		return ResponseEntity.ok(certificateDTO);			
	}

	@PostMapping(value = "/request")
	public ResponseEntity<CertificateRequestDTO> request(@RequestBody CertificateRequestDTO requestDTO) {
		this.certificateService.sendRequest(requestDTO);
		return ResponseEntity.ok(requestDTO);
	}

	@DeleteMapping(value = "/{fileName}")
	public ResponseEntity<Void> revoke(@PathVariable String fileName) {
		this.certificateService.sendRevokeRequest(fileName);
		return ResponseEntity.noContent().build();
	}
}
