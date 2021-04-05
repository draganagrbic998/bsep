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
import com.example.demo.service.UserService;
import com.example.demo.utils.Constants;

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
import java.security.cert.X509Certificate;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class CertificatesController {

	private final CertificateService certificateService;
	private final CertificateInfoService certificateInfoService;
	private final CertificateInfoMapper certificateInfoMapper;
	private final UserService userService;

	@Autowired
	public CertificatesController(CertificateService certificateService, CertificateInfoService certificateInfoService,
			CertificateInfoMapper certificateInfoMapper, UserService userService) {
		this.certificateService = certificateService;
		this.certificateInfoService = certificateInfoService;
		this.certificateInfoMapper = certificateInfoMapper;
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<CreateCertificateDTO> create(@Valid @RequestBody CreateCertificateDTO certificateDTO) {
		this.certificateService.create(certificateDTO);
		return ResponseEntity.ok(certificateDTO);
	}

	@PreAuthorize("permitAll()")
	@PostMapping(value = "/requests")
	public ResponseEntity<Void> createRequest(@Valid @RequestBody CertificateRequestDTO requestDTO, HttpServletRequest request) {
		//i dodaj ti ipak ovde validaicju isto ovog sertifikata
		if (this.userService.findOne(requestDTO.getEmail()) == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
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
	public ResponseEntity<Void> revokeRequest(@Valid @RequestBody RevokeRequestDTO revokeRequestDTO, HttpServletRequest request) {
		if (this.certificateService.isCertificateValid(((X509Certificate[]) request.getAttribute(Constants.CERT_ATTRIBUTE))[0].getSerialNumber().longValue())) {
			try {
				this.certificateService.revoke(revokeRequestDTO.getSerial(), Constants.REVOKE_REQUEST_REASON);
				return ResponseEntity.ok().build();			
			}
			catch(Exception e) {
				;
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}

	@GetMapping(value = "/alias/{alias}")
	public ResponseEntity<CertificateInfoDTO> getByAlias(@PathVariable String alias) {
		return ResponseEntity.ok(this.certificateInfoMapper.mapToDto(this.certificateInfoService.findByAlias(alias)));
	}

	@PreAuthorize("permitAll()")
	@PostMapping(value = "/validate")
	public ResponseEntity<Boolean> validate(@Valid @RequestBody ValidationRequestDTO validationRequestDTO) {
		return ResponseEntity.ok(this.certificateService.isCertificateValid(validationRequestDTO.getSerial()));
	}
}
