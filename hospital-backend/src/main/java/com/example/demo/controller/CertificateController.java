package com.example.demo.controller;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.dto.CertificateDTO;
import com.example.demo.service.CertificateService;
import com.example.demo.utils.Constants;

import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {

	@Autowired
	private CertificateService certificateService;

	@PreAuthorize("permitAll()")
	@PostMapping
	public ResponseEntity<CertificateDTO> create(@RequestBody CertificateDTO certificateDTO, HttpServletRequest request) {
		//ovde ne radim validaciju sertifikata
		//a ovde ne treba da se poziva truststore jer je prvi put
		//dodaj ti ipa ovde validaciju roota
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
	@DeleteMapping(value = "/{fileName}")
	public ResponseEntity<Void> revoke(@PathVariable String fileName) {
		this.certificateService.sendRevokeRequest(fileName);
		return ResponseEntity.noContent().build();
	}
}
