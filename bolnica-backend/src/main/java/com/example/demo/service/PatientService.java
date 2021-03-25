package com.example.demo.service;

import java.util.Random;

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
		return this.patientRepository.findById(id).get();
	}
	
	@Transactional(readOnly = false)
	public Patient save(Patient patient) {
		return this.patientRepository.save(patient);
	}

	@Transactional(readOnly = false)
	public void delete(long id) {
		this.patientRepository.deleteById(id);
	}

	public Patient findByInsuredNumber(String insuredNumber) {
		if (this.patientRepository.findByInsuredNumber(insuredNumber) == null && this.patientRepository.count() == 0) {
			return null;
		}
		int length = (int) this.patientRepository.count();
		return this.patientRepository.findById(this.patientRepository.findIds().get(new Random().nextInt(length))).get();
	}

}
