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
@RequestMapping(value = "/api/configuration", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")	
@AllArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @GetMapping
    public ResponseEntity<Configuration> get() {
        return ResponseEntity.ok(this.configurationService.get());
    }

    @PutMapping
    public ResponseEntity<Void> set(@Valid @RequestBody Configuration configuration) {
        this.configurationService.set(configuration);
        return ResponseEntity.ok().build();
    }


}
