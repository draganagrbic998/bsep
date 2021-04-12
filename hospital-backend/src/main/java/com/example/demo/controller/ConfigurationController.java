package com.example.demo.controller;

import com.example.demo.service.ConfigurationService;
import com.example.demo.utils.Configuration;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api/configuration", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @GetMapping
    public ResponseEntity<Configuration> get() {
        return ResponseEntity.ok(configurationService.get());
    }

    @PutMapping
    public ResponseEntity<Void> set(@RequestBody Configuration configuration) throws IOException {
        configurationService.set(configuration);
        return ResponseEntity.ok().build();
    }


}
