package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.PatientDTO;
import com.example.demo.model.Patient;
import com.example.demo.service.PatientService;
import com.example.demo.utils.DatabaseCipher;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PatientMapper {

	private final PatientService patientService;
	private final DatabaseCipher databaseCipher;

	public Patient map(PatientDTO patientDTO) {
		Patient patient = new Patient();
		this.setModel(patient, patientDTO);
		return this.databaseCipher.encrypt(patient);
	}
	
	public Patient map(long id, PatientDTO patientDTO) {
		Patient patient = this.patientService.findOne(id);
		this.setModel(this.databaseCipher.decrypt(patient), patientDTO);
		return this.databaseCipher.encrypt(patient);
	}
	
	public PatientDTO map(Patient patient) {
		return new PatientDTO(this.databaseCipher.decrypt(patient));
	}
	
	private void setModel(Patient patient, PatientDTO patientDTO) {
		patient.setFirstName(patientDTO.getFirstName());
		patient.setLastName(patientDTO.getLastName());
		patient.setBirthDate(patientDTO.getBirthDate());
		patient.setGender(patientDTO.getGender());
		patient.setBlodType(patientDTO.getBlodType());
		patient.setHeight(patientDTO.getHeight());
		patient.setWeight(patientDTO.getWeight());
		patient.setAddress(patientDTO.getAddress());
		patient.setCity(patientDTO.getCity());
	}
	
}
