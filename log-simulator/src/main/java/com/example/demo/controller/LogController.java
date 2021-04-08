package com.example.demo.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LogDTO;
import com.example.demo.service.LogService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/logs", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class LogController {

	private final LogService logService;
	
	@GetMapping
	public ResponseEntity<List<LogDTO>> findAll() {
		return ResponseEntity.ok(this.logService.findAll());
	}
	
}
