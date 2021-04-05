package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.mapper.CertificateRequestMapper;
import com.example.demo.service.CertificateRequestService;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
@AllArgsConstructor
public class CertificateRequestController {

	private final CertificateRequestService certificateService;
	private final UserService userService;
	private final CertificateRequestMapper certificateMapper;
		
	@GetMapping(value = "/requests")
	public ResponseEntity<Page<CertificateRequestDTO>> findAllRequests(Pageable pageable) {
		return ResponseEntity.ok(this.certificateService.findAll(pageable).map(CertificateRequestDTO::new));
	}

	@PreAuthorize("permitAll()")
	@PostMapping(value = "/requests")
	public ResponseEntity<Void> createRequest(@Valid @RequestBody CertificateRequestDTO requestDTO, HttpServletRequest request) {
		//i dodaj ti ipak ovde validaicju isto ovog sertifikata
		if (this.userService.findOne(requestDTO.getEmail()) == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		this.certificateService.save(this.certificateMapper.map(requestDTO));
		return ResponseEntity.ok().build();			
	}

	
}
