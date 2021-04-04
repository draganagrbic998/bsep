package com.example.demo.controller;

import com.example.demo.dto.CertificateInfoDTO;
import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.dto.CreateCertificateDTO;
import com.example.demo.dto.RevokeDTO;
import com.example.demo.dto.RevokeRequestDTO;
import com.example.demo.dto.ValidationRequestDTO;
import com.example.demo.mapper.CertificateInfoMapper;
import com.example.demo.service.CertificateInfoService;
import com.example.demo.service.CertificateService;
import com.example.demo.utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class CertificatesController {

	private final CertificateService certificateService;
	private final CertificateInfoService certificateInfoService;
	private final CertificateInfoMapper certificateInfoMapper;

	@Autowired
	public CertificatesController(CertificateService certificateService, CertificateInfoService certificateInfoService,
			CertificateInfoMapper certificateInfoMapper) {
		this.certificateService = certificateService;
		this.certificateInfoService = certificateInfoService;
		this.certificateInfoMapper = certificateInfoMapper;
	}

	@PostMapping
	public ResponseEntity<CreateCertificateDTO> create(@Valid @RequestBody CreateCertificateDTO certificateDTO) {
		this.certificateService.create(certificateDTO);
		return ResponseEntity.ok(certificateDTO);
	}

	@PreAuthorize("permitAll()")
	@PostMapping(value = "/requests")
	public ResponseEntity<Void> createRequest(@Valid @RequestBody CertificateRequestDTO requestDTO) {
		if (!requestDTO.getPath().equalsIgnoreCase("https://localhost:8081/api/certificates")
				&& !requestDTO.getPath().equalsIgnoreCase("https://localhost:8082/api/certificates"))
			return ResponseEntity.badRequest().build();
		this.certificateService.createRequest(requestDTO);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<Page<CertificateInfoDTO>> findAll(Pageable pageable) {
		return ResponseEntity.ok(this.certificateInfoService.findAll(pageable)
				.map(certificateInfo -> this.certificateInfoMapper.mapToDto(certificateInfo, 0)));
	}

	@GetMapping(value = "/requests")
	public ResponseEntity<Page<CertificateRequestDTO>> findAllRequests(Pageable pageable) {
		return ResponseEntity.ok(this.certificateService.findAllRequests(pageable)
				.map(certificateRequest -> new CertificateRequestDTO(certificateRequest)));
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

	@PutMapping
	public ResponseEntity<Void> revoke(@Valid @RequestBody RevokeDTO revokeDTO) throws MessagingException {
		this.certificateService.revoke(revokeDTO.getId(), revokeDTO.getReason());
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("permitAll()")
	@PostMapping(value = "/requests/revoke")
	public ResponseEntity<Void> revokeRequest(@Valid @RequestBody RevokeRequestDTO revokeRequestDTO)
			throws MessagingException {
		if (!revokeRequestDTO.getPath().equalsIgnoreCase("https://localhost:8081"))
			return ResponseEntity.badRequest().build();
		this.certificateService.revoke(revokeRequestDTO.getSerial(), Constants.REVOKE_REQUEST_REASON);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/alias/{alias}")
	public ResponseEntity<CertificateInfoDTO> getByAlias(@PathVariable String alias) {
		return ResponseEntity.ok(this.certificateInfoMapper.mapToDto(this.certificateInfoService.findByAlias(alias)));
	}

	@PreAuthorize("permitAll()")
	@GetMapping(value = "/validate")
	public ResponseEntity<Boolean> validate(@Valid @RequestBody ValidationRequestDTO validationRequestDTO) {
		if (!validationRequestDTO.getPath().equalsIgnoreCase("https://localhost:8081/api/certificates"))
			return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(this.certificateService.isCertificateValid(validationRequestDTO.getSerial()));
	}
}
