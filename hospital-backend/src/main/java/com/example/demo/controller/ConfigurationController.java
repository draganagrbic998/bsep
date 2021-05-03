package com.example.demo.controller;

import com.example.demo.model.Configuration;
import com.example.demo.service.ConfigurationService;

import lombok.AllArgsConstructor;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/configuration", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")	
@AllArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @GetMapping
    public ResponseEntity<Configuration> getConfiguration() {
        return ResponseEntity.ok(this.configurationService.getConfiguration());
    }

    @PutMapping
    public ResponseEntity<Void> setConfiguration(@Valid @RequestBody Configuration configuration) {
        this.configurationService.setConfiguration(configuration);
        return ResponseEntity.ok().build();
    }

}
