package com.example.demo.controller;

import com.example.demo.dto.ConfigurationDTO;
import com.example.demo.model.Configuration;
import com.example.demo.service.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/configuration", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('READ_CONFIGURATION')")
@AllArgsConstructor
public class ConfigurationController {

	private final ConfigurationService configurationService;

	@PostMapping
	public ResponseEntity<Configuration> getConfiguration(@Valid @RequestBody ConfigurationDTO target) {
		return ResponseEntity.ok(this.configurationService.getConfiguration(target.getUrl()));
	}

	@PutMapping
	@PreAuthorize("hasAuthority('SAVE_CONFIGURATION')")
	public ResponseEntity<Void> setConfiguration(@Valid @RequestBody ConfigurationDTO target) {
		this.configurationService.setConfiguration(target.getUrl(), target.getConfiguration());
		return ResponseEntity.ok().build();
	}
}
