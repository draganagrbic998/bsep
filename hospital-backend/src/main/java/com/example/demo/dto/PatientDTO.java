package com.example.demo.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.example.demo.model.BlodType;
import com.example.demo.model.Gender;
import com.example.demo.model.Patient;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientDTO {

	private Long id;
	private String insuredNumber;
	private double height;
	private double weight;

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotNull
	private LocalDate birthDate;
	
	@NotNull
	private Gender gender;
	
	@NotNull
	private BlodType blodType;
		
	@NotBlank
	private String address;
	
	@NotBlank
	private String city;
			
	public PatientDTO(Patient patient) {
		super();
		this.id = patient.getId();
		this.insuredNumber = patient.getInsuredNumber();
		this.height = patient.getHeight();
		this.weight = patient.getWeight();
		this.firstName = patient.getFirstName();
		this.lastName = patient.getLastName();
		this.birthDate = patient.getBirthDate();
		this.gender = patient.getGender();
		this.blodType = patient.getBlodType();
		this.address = patient.getAddress();
		this.city = patient.getCity();
	}
	
}
