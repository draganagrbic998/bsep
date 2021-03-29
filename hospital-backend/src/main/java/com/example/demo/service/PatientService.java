package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;

@Service
@Transactional(readOnly = true)
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
		
	public Page<Patient> findAll(Pageable pageable, String search) {
		return this.patientRepository.findAll(pageable, search);
	}
	
	public Patient find(long id) {
		return this.patientRepository.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = false)
	public Patient save(Patient patient) {
		return this.patientRepository.save(patient);
	}

	@Transactional(readOnly = false)
	public void delete(long id) {
		this.patientRepository.deleteById(id);
	}

}
