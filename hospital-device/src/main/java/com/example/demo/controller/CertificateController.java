package com.example.demo.controller;

import com.example.demo.dto.CertificateDTO;
import com.example.demo.service.CertificateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

}
