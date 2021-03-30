package com.example.demo.controller;

import com.example.demo.dto.CertificateInfoDTO;
import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.dto.CreateCertificateDTO;
import com.example.demo.mapper.CertificateInfoMapper;
import com.example.demo.mapper.CertificateRequestMapper;
import com.example.demo.service.CertificateInfoService;
import com.example.demo.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/certificates")
@PreAuthorize("hasAuthority('SUPER_ADMIN')")	
public class CertificatesController {

	private final CertificateService certificateService;
	private final CertificateInfoService certificateInfoService;
	private final CertificateInfoMapper infoMapper;
	private final CertificateRequestMapper requestMapper;
	@Autowired
	public CertificatesController(CertificateService certificateService,
								  CertificateInfoService certificateInfoService,
								  CertificateInfoMapper certificateInfoMapper,
								  CertificateRequestMapper requestMapper) {
		this.certificateService = certificateService;
		this.certificateInfoService = certificateInfoService;
		this.infoMapper = certificateInfoMapper;
		this.requestMapper = requestMapper;
	}

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

	@GetMapping(value = "/download-crt/{alias}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadCrt(@PathVariable String alias) throws IOException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(certificateService.getCrt(alias).getBytes());
		int length = inputStream.available();
		InputStreamResource resource = new InputStreamResource(inputStream);
		return ResponseEntity.ok().contentLength(length).body(resource);
	}

	@GetMapping(value = "/download-key/{alias}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadKey(@PathVariable String alias) throws IOException {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(certificateService.getKey(alias).getBytes());
		int length = inputStream.available();
		InputStreamResource resource = new InputStreamResource(inputStream);
		return ResponseEntity.ok().contentLength(length).body(resource);
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
