package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PatientDTO;
import com.example.demo.mapper.PatientMapper;
import com.example.demo.model.Patient;
import com.example.demo.service.PatientService;
import com.example.demo.utils.Constants;

@RestController
@RequestMapping(value = "/api/patients", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PatientMapper patientMapper;
	
	@GetMapping
	public ResponseEntity<List<PatientDTO>> findAll(Pageable pageable, @RequestParam String search, HttpServletResponse response){
		Page<Patient> patients = this.patientService.findAll(pageable, search);
		response.setHeader(Constants.ENABLE_HEADER, Constants.FIRST_PAGE_HEADER + ", " + Constants.LAST_PAGE_HEADER);
		response.setHeader(Constants.FIRST_PAGE_HEADER, patients.isFirst() + "");
		response.setHeader(Constants.LAST_PAGE_HEADER, patients.isLast() + "");
		return new ResponseEntity<>(this.patientMapper.map(patients.toList()), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<PatientDTO> create(@Valid @RequestBody PatientDTO patientDTO){
		patientDTO.setId(null);
		return new ResponseEntity<>(this.patientMapper.map(this.patientService.save(this.patientMapper.map(patientDTO))), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		this.patientService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
