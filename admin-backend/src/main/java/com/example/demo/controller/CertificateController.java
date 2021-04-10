package com.example.demo.controller;

import com.example.demo.dto.RevokeDTO;
import com.example.demo.dto.certificate.CertificateInfoDTO;
import com.example.demo.dto.certificate.CertificateRequestDTO;
import com.example.demo.dto.certificate.CreateCertificateDTO;
import com.example.demo.mapper.CertificateInfoMapper;
import com.example.demo.service.CertificateInfoService;
import com.example.demo.service.CertificateRequestService;
import com.example.demo.service.CertificateService;
import com.example.demo.service.KeyExportService;

import lombok.AllArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
@AllArgsConstructor
public class CertificateController {

	private final CertificateService certificateService;
	private final CertificateInfoService certificateInfoService;
	private final CertificateRequestService certificateRequestService;
	private final CertificateInfoMapper certificateInfoMapper;
	private final KeyExportService keyExportService;

	@GetMapping
	public ResponseEntity<Page<CertificateInfoDTO>> findAll(Pageable pageable) {
		return ResponseEntity.ok(this.certificateInfoService.findAll(pageable).map(ci -> this.certificateInfoMapper.map(ci, 0)));
	}

	@GetMapping(value = "/{alias}")
	public ResponseEntity<CertificateInfoDTO> findByAlias(@PathVariable String alias) {
		return ResponseEntity.ok(this.certificateInfoMapper.map(this.certificateInfoService.findByAlias(alias), 1));
	}

	@GetMapping(value = "/requests")
	public ResponseEntity<Page<CertificateRequestDTO>> findAllRequests(Pageable pageable) {
		return ResponseEntity.ok(this.certificateRequestService.findAll(pageable).map(CertificateRequestDTO::new));
	}

	@PostMapping
	public ResponseEntity<CreateCertificateDTO> create(@Valid @RequestBody CreateCertificateDTO certificateDTO) {
		this.certificateService.create(certificateDTO);
		return ResponseEntity.ok(certificateDTO);
	}

	@PutMapping
	public ResponseEntity<Void> revoke(@Valid @RequestBody RevokeDTO revokeDTO) {
		this.certificateInfoService.revoke(revokeDTO.getId(), revokeDTO.getReason());
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/download-crt/{alias}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadCrt(@PathVariable String alias) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(this.keyExportService.getCrt(alias).getBytes());
		int length = inputStream.available();
		InputStreamResource resource = new InputStreamResource(inputStream);
		return ResponseEntity.ok().contentLength(length).body(resource);
	}

	@GetMapping(value = "/download-key/{alias}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadKey(@PathVariable String alias) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(this.keyExportService.getKey(alias).getBytes());
		int length = inputStream.available();
		InputStreamResource resource = new InputStreamResource(inputStream);
		return ResponseEntity.ok().contentLength(length).body(resource);
	}

}
