package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.PatientDTO;
import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;

@Component
public class PatientMapper {

	@Autowired
	private PatientRepository patientRepository;
	
	public Patient map(PatientDTO patientDTO) {
		Patient patient = new Patient();
		this.setModel(patient, patientDTO);
		return patient;
	}
	
	public Patient map(long id, PatientDTO patientDTO) {
		Patient patient = this.patientRepository.findById(id).get();
		this.setModel(patient, patientDTO);
		return patient;
	}

	public PatientDTO map(Patient patient) {
		return new PatientDTO(patient);
	}
	
	public List<PatientDTO> map(List<Patient> patients){
		return patients.stream().map(PatientDTO::new).collect(Collectors.toList());
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