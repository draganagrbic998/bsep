package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LogDTO;
import com.example.demo.dto.LogSearchDTO;
import com.example.demo.service.LogService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/logs", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class LogController {

	private final LogService logService;

	@PostMapping
	@PreAuthorize("hasAuthority('READ_LOGS')")	
	public ResponseEntity<Page<LogDTO>> findAll(Pageable pageable, @Valid @RequestBody LogSearchDTO searchDTO){
		return ResponseEntity.ok(this.logService.findAll(pageable, searchDTO.getMode(), searchDTO.getStatus(), 
				searchDTO.getIpAddress(), searchDTO.getDescription(), searchDTO.getDate()).map(LogDTO::new));
	}
	
}
