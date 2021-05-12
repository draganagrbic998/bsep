package com.example.demo.controller;

import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ReportDTO;
import com.example.demo.service.ReportService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ReportController {

	private final ReportService reportService;

	@GetMapping
	@PreAuthorize("hasAuthority('READ_REPORT')")	
	public ResponseEntity<ReportDTO> report(@RequestParam(required=false) Date start, @RequestParam(required=false) Date end){
		return ResponseEntity.ok(this.reportService.report(start, end));
	}
	
}
