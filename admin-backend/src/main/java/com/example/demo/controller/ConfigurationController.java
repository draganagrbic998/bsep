package com.example.demo.controller;

import com.example.demo.dto.ConfigurationTarget;
import com.example.demo.dto.RevokeDTO;
import com.example.demo.dto.certificate.CertificateInfoDTO;
import com.example.demo.dto.certificate.CertificateRequestDTO;
import com.example.demo.dto.certificate.CreateCertificateDTO;
import com.example.demo.mapper.CertificateInfoMapper;
import com.example.demo.model.Configuration;
import com.example.demo.service.*;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;

@RestController
@RequestMapping(value = "/api/configuration", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
@AllArgsConstructor
public class ConfigurationController {

	private final ConfigurationService configurationService;

	@PostMapping
	public ResponseEntity<Configuration> getConfiguration(@RequestBody ConfigurationTarget target) {
		return ResponseEntity.ok(configurationService.getConfiguration(target.getUrl()));
	}

	@PutMapping
	public ResponseEntity<Void> saveConfiguration(@RequestBody ConfigurationTarget target) {
		this.configurationService.set(target.getUrl(), target.getConfiguration());
		return ResponseEntity.ok().build();
	}
}
