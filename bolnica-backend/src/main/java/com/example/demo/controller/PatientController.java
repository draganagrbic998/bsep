package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PatientDTO;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.service.PatientService;

@RestController
@RequestMapping(value = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PatientMapper patientMapper;
	
	@PostMapping
	public ResponseEntity<PatientDTO> create(@Valid @RequestBody PatientDTO patientDTO){
		return new ResponseEntity<>(this.patientMapper.map(this.patientService.save(this.patientMapper.map(patientDTO))), HttpStatus.CREATED);
	}
	
}
