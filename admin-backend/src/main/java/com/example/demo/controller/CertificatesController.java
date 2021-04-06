package com.example.demo.controller;

import com.example.demo.dto.RevokeDTO;
import com.example.demo.dto.RevokeRequestDTO;
import com.example.demo.dto.ValidationRequestDTO;
import com.example.demo.dto.certificate.CertificateInfoDTO;
import com.example.demo.dto.certificate.CreateCertificateDTO;
import com.example.demo.service.CertificateInfoService;
import com.example.demo.service.CertificateService;
import com.example.demo.service.CertificateValidationService;
import com.example.demo.service.KeyExportService;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

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
import java.security.cert.X509Certificate;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
@AllArgsConstructor
public class CertificatesController {

	private final CertificateValidationService certificateValidationService;
	private final CertificateInfoService certificateInfoService;
	private final CertificateService certificateService;
	private final KeyExportService keyExportService;

	@GetMapping
	public ResponseEntity<Page<CertificateInfoDTO>> findAll(Pageable pageable) {
		return ResponseEntity.ok(this.certificateInfoService.findAll(pageable).map(ci -> new CertificateInfoDTO(ci, 0)));
	}

	@GetMapping(value = "/{alias}")
	public ResponseEntity<CertificateInfoDTO> findByAlias(@PathVariable String alias) {
		return ResponseEntity.ok(new CertificateInfoDTO(this.certificateInfoService.findByAlias(alias), 1));
	}

	@PostMapping
	public ResponseEntity<CreateCertificateDTO> create(@Valid @RequestBody CreateCertificateDTO certificateDTO) {
		this.certificateService.create(certificateDTO);
		return ResponseEntity.ok(certificateDTO);
	}

	@PutMapping
	public ResponseEntity<Void> revoke(@Valid @RequestBody RevokeDTO revokeDTO) {
		try {
			this.certificateInfoService.revoke(revokeDTO.getId(), revokeDTO.getReason());
			return ResponseEntity.noContent().build();
		}
		catch(MessagingException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping(value = "/download-crt/{alias}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadCrt(@PathVariable String alias) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(this.keyExportService.getCrt(alias).getBytes());
			int length = inputStream.available();
			InputStreamResource resource = new InputStreamResource(inputStream);
			return ResponseEntity.ok().contentLength(length).body(resource);
		}
		catch(IOException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping(value = "/download-key/{alias}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<InputStreamResource> downloadKey(@PathVariable String alias) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(this.keyExportService.getKey(alias).getBytes());
			int length = inputStream.available();
			InputStreamResource resource = new InputStreamResource(inputStream);
			return ResponseEntity.ok().contentLength(length).body(resource);
		}
		catch(IOException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PreAuthorize("permitAll()")
	@PostMapping(value = "/validate")
	public ResponseEntity<Boolean> validate(@Valid @RequestBody ValidationRequestDTO validationDTO) {
		return ResponseEntity.ok(this.certificateValidationService.isCertificateValid(validationDTO.getSerial()));
	}

	@PreAuthorize("permitAll()")
	@PostMapping(value = "/revoke")
	public ResponseEntity<Void> revoke(@Valid @RequestBody RevokeRequestDTO revokeDTO, HttpServletRequest request) throws MessagingException {
		if (!this.certificateValidationService.isCertificateValid(((X509Certificate[]) 
				request.getAttribute(Constants.CERT_ATTRIBUTE))[0].getSerialNumber().longValue())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		this.certificateInfoService.revoke(revokeDTO.getSerial(), "Revocation requested by hospital admin.");
		return ResponseEntity.ok().build();			
	}

}
