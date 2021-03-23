package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.PatientDTO;
import com.example.demo.model.Patient;

@Component
public class PatientMapper {

	public Patient map(PatientDTO patientDTO) {
		Patient patient = new Patient();
		patient.setId(patientDTO.getId());
		patient.setInsuredNumber(patientDTO.getInsuredNumber());
		patient.setName(patientDTO.getName());
		patient.setSurname(patientDTO.getSurname());
		patient.setBirthDate(patientDTO.getBirthDate());
		patient.setGender(patientDTO.getGender());
		patient.setBlodType(patientDTO.getBlodType());
		patient.setHeight(patientDTO.getHeight());
		patient.setWeight(patientDTO.getWeight());
		patient.setAddress(patientDTO.getAddress());
		patient.setCity(patientDTO.getCity());
		return patient;
	}
	
	public PatientDTO map(Patient patient) {
		return new PatientDTO(patient);
	}
	
}
