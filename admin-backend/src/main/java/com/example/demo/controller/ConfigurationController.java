package com.example.demo.controller;

import com.example.demo.dto.ConfigurationTarget;
import com.example.demo.model.Configuration;
import com.example.demo.service.*;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/configuration", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
@AllArgsConstructor
public class ConfigurationController {

	private final ConfigurationService configurationService;

	@PostMapping
	public ResponseEntity<Configuration> get(@Valid @RequestBody ConfigurationTarget target) {
		return ResponseEntity.ok(this.configurationService.get(target.getUrl()));
	}

	@PutMapping
	public ResponseEntity<Void> set(@Valid @RequestBody ConfigurationTarget target) {
		this.configurationService.set(target.getUrl(), target.getConfiguration());
		return ResponseEntity.ok().build();
	}
}
