package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Patient;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.repository.PatientRepository;
import com.example.demo.utils.DatabaseCipher;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;
	private final DatabaseCipher databaseCipher;
	private final Logger logger;
	
	@Transactional(readOnly = true)
	public Page<Patient> findAll(Pageable pageable, String search) {
		try {
			Page<Patient> response = this.patientRepository.findAll(pageable, this.databaseCipher.encrypt(search));
			this.logger.write(LogStatus.SUCCESS, String.format("Patients page number %d successfully fetched.", pageable.getPageNumber()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching patients page number %d.", pageable.getPageNumber()));
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public Patient findOne(long id) {
		try {
			Patient response = this.patientRepository.findById(id).orElse(null);
			this.logger.write(LogStatus.SUCCESS, String.format("Patient with id %d successfully fetched.", id));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching patient with id %d.", id));
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public Patient save(Patient patient) {
		try {
			Patient response = this.patientRepository.save(patient);
			this.logger.write(LogStatus.SUCCESS, String.format("Patient with id %d successfully saved.", response.getId()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while saving patient.");
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public void delete(long id) {
		try {
			this.patientRepository.deleteById(id);
			this.logger.write(LogStatus.SUCCESS, String.format("Patient with id %d successfully deleted.", id));
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while deleting patient with id %d.", id));
			throw e;
		}
	}

}
