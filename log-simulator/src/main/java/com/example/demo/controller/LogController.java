package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LogDTO;
import com.example.demo.service.LogService;

@RestController
@RequestMapping(value = "/api/logs", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogController {

	@Autowired
	private LogService logService;
	
	@GetMapping
	public ResponseEntity<List<LogDTO>> findAll(){
		return ResponseEntity.ok(this.logService.findAll().stream().map(LogDTO::new).collect(Collectors.toList()));
	}
	
}
