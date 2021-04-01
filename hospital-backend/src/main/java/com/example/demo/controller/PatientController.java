package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PatientDTO;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.service.PatientService;

@RestController
@RequestMapping(value = "/api/patients", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('DOCTOR')")	
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PatientMapper patientMapper;
			
	@GetMapping
	public ResponseEntity<Page<PatientDTO>> findAll(Pageable pageable, @RequestParam String search){
		return ResponseEntity.ok(this.patientService.findAll(pageable, search).map(PatientDTO::new));
	}

	@PostMapping
	public ResponseEntity<PatientDTO> create(@Valid @RequestBody PatientDTO patientDTO){
		return ResponseEntity.ok(new PatientDTO(this.patientMapper.map(patientDTO)));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<PatientDTO> update(@PathVariable long id, @Valid @RequestBody PatientDTO patientDTO){
		return ResponseEntity.ok(new PatientDTO(this.patientMapper.map(id, patientDTO)));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		this.patientService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
