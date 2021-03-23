package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.dto.PatientDTO;
import com.example.demo.model.Patient;

@Component
public class PatientMapper {

	public Patient map(PatientDTO patientDTO) {
		Patient patient = new Patient();
		patient.setId(patientDTO.getId());
		patient.setInsuredNumber(patientDTO.getInsuredNumber());
		patient.setFirstName(patientDTO.getFirstName());
		patient.setLastName(patientDTO.getLastName());
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
	
	public List<PatientDTO> map(List<Patient> patients){
		return patients.stream().map(PatientDTO::new).collect(Collectors.toList());
	}
	
}
