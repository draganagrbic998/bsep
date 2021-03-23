package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;

@Service
@Transactional(readOnly = true)
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	@Transactional(readOnly = false)
	public Patient save(Patient patient) {
		return this.patientRepository.save(patient);
	}
	
}
