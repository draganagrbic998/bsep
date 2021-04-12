package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReportDTO;
import com.example.demo.dto.ReportSearchDTO;
import com.example.demo.service.ReportService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('ADMIN')")	
@AllArgsConstructor
public class ReportController {

	private final ReportService reportService;

	@PostMapping
	public ResponseEntity<ReportDTO> report(@RequestBody ReportSearchDTO searchDTO){
		return ResponseEntity.ok(this.reportService.report(searchDTO));
	}
	
}
