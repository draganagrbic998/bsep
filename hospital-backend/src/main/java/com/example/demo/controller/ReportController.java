package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping(value = "/api/report", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('ADMIN')")	
public class ReportController {

	@Autowired
	private ReportService reportService;
	
	@PostMapping
	public ResponseEntity<ReportDTO> report(@RequestBody ReportSearchDTO searchDTO){
		return new ResponseEntity<>(this.reportService.report(searchDTO), HttpStatus.OK);
	}
	
}
