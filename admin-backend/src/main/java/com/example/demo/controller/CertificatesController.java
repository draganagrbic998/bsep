package com.example.demo.controller;

import com.example.demo.dto.CertificateInfoDTO;
import com.example.demo.dto.CreateCertificateDTO;
import com.example.demo.mapper.CertificateInfoMapper;
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
@RequestMapping(value = "/api/certificates")
@PreAuthorize("hasAuthority('SUPER_ADMIN')")	
public class CertificatesController {

	@Autowired
	private CertificateService certificateService;

	@Autowired
	private CertificateInfoService certificateInfoService;

	@Autowired
	private CertificateInfoMapper mapper;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@RequestBody CreateCertificateDTO createCertificateDto) {
		try {
			this.certificateService.createCertificate(createCertificateDto);
			return ResponseEntity.created(URI.create("wontneedyou")).build();
		}
		catch(Exception e) {
			return null;
		}
	}

	@GetMapping
	public ResponseEntity<Page<CertificateInfoDTO>> findAll(Pageable pageable) {
		return ResponseEntity.ok(this.certificateInfoService.findAll(pageable).map(certificateInfo -> mapper.mapToDto(certificateInfo, 0)));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> revoke(@PathVariable Long id) {
		this.certificateService.revoke(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/alias/{alias}")
	public ResponseEntity<CertificateInfoDTO> getByAlias(@PathVariable String alias) {
		return ResponseEntity.ok(this.mapper.mapToDto(this.certificateInfoService.findByAlias(alias)));
	}

}
