package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import com.example.demo.utils.DatabaseCipher;

import lombok.AllArgsConstructor;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;
	private final DatabaseCipher databaseCipher;
	
	public Page<Patient> findAll(Pageable pageable, String search) {
		return this.patientRepository.findAll(pageable, this.databaseCipher.encrypt(search));
	}

	public Patient findOne(long id) {
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
