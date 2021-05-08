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
import java.io.IOException;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('READ_CERTIFICATES')")
@AllArgsConstructor
public class CertificateController {

	private final CertificateService certificateService;
	private final CertificateInfoService certificateInfoService;
	private final CertificateRequestService certificateRequestService;
	private final CertificateInfoMapper certificateInfoMapper;
	private final KeyExportService keyExportService;

	@GetMapping
	public ResponseEntity<Page<CertificateInfoDTO>> findAll(Pageable pageable) {
		return ResponseEntity.ok(this.certificateInfoService.findAll(pageable).map(certificate -> this.certificateInfoMapper.map(certificate, 0)));
	}

	@GetMapping("/{alias}")
	public ResponseEntity<CertificateInfoDTO> findByAlias(@PathVariable String alias) {
		return ResponseEntity.ok(this.certificateInfoMapper.map(this.certificateInfoService.findByAlias(alias), 1));
	}

	@GetMapping("/requests")
	public ResponseEntity<Page<CertificateRequestDTO>> findAllRequests(Pageable pageable) {
		return ResponseEntity.ok(this.certificateRequestService.findAll(pageable).map(CertificateRequestDTO::new));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('SAVE_CERTIFICATES')")
	public ResponseEntity<CreateCertificateDTO> create(@Valid @RequestBody CreateCertificateDTO certificateDTO) {
		this.certificateService.create(certificateDTO);
		return ResponseEntity.ok(certificateDTO);
	}

	@PutMapping
	@PreAuthorize("hasAuthority('REVOKE_CERTIFICATES')")
	public ResponseEntity<CertificateInfoDTO> revoke(@Valid @RequestBody RevokeDTO revokeDTO) {
		return ResponseEntity.ok(new CertificateInfoDTO(this.certificateInfoService.revoke(revokeDTO.getId(), revokeDTO.getReason())));
	}

	@GetMapping(path = "/download-crt/{alias}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadCrt(@PathVariable String alias) {
		ByteArrayInputStream in = new ByteArrayInputStream(this.keyExportService.getCrt(alias).getBytes());
		int length = in.available();
		InputStreamResource resource = new InputStreamResource(in);
		return ResponseEntity.ok().contentLength(length).body(resource);
	}

	@GetMapping(path = "/download-key/{alias}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadKey(@PathVariable String alias) {
		ByteArrayInputStream in = new ByteArrayInputStream(this.keyExportService.getKey(alias).getBytes());
		int length = in.available();
		InputStreamResource resource = new InputStreamResource(in);
		return ResponseEntity.ok().contentLength(length).body(resource);
	}

	@GetMapping(path = "/download-jks/{issuerAlias}/{alias}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadJks(@PathVariable String alias,
														   @PathVariable String issuerAlias) throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(this.certificateService.getJks(issuerAlias, alias));
		int length = in.available();
		InputStreamResource resource = new InputStreamResource(in);
		return ResponseEntity.ok().contentLength(length).body(resource);
	}

}
