package com.example.demo.controller;

import com.example.demo.dto.CertificateInfoDTO;
import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.dto.CreateCertificateDTO;
import com.example.demo.mapper.CertificateInfoMapper;
import com.example.demo.mapper.CertificateRequestMapper;
import com.example.demo.service.CertificateInfoService;
import com.example.demo.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
	private CertificateInfoMapper infoMapper;

	@Autowired
	private CertificateRequestMapper requestMapper;
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> create(@RequestBody CreateCertificateDTO createCertificateDto) {
		this.certificateService.createCertificate(createCertificateDto);
		return ResponseEntity.created(URI.create("wontneedyou")).build();
	}

	@PostMapping(value = "/create_request", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> createRequest(@RequestBody CertificateRequestDTO certificateRequestDTO) {
		this.certificateService.createCertificateRequest(certificateRequestDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Page<CertificateInfoDTO>> findAll(Pageable pageable) {
		return ResponseEntity.ok(this.certificateInfoService.findAll(pageable).map(certificateInfo -> this.infoMapper.mapToDto(certificateInfo, 0)));
	}

	@GetMapping(value = "/requests")
	public ResponseEntity<Page<CertificateRequestDTO>> findAllRequests(Pageable pageable) {
		return ResponseEntity.ok(this.certificateService.findAllRequests(pageable).map(certificateRequest -> this.requestMapper.map(certificateRequest)));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> revoke(@PathVariable long id) {
		this.certificateService.revoke(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/alias/{alias}")
	public ResponseEntity<CertificateInfoDTO> getByAlias(@PathVariable String alias) {
		return ResponseEntity.ok(this.infoMapper.mapToDto(this.certificateInfoService.findByAlias(alias)));
	}

}
