package com.example.demo.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.demo.model.BlodType;
import com.example.demo.model.Gender;
import com.example.demo.model.Patient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientDTO {

	@NotBlank(message = "First name can't be blank")
	private String firstName;
	
	@NotBlank(message = "Last name can't be blank")
	private String lastName;
	
	@NotNull(message = "Birth date can't be null")
	private Date birthDate;
	
	@NotNull(message = "Gender can't be null")
	private Gender gender;
	
	@NotNull(message = "Blod type can't be null")
	private BlodType blodType;
		
	@NotBlank(message = "Address can't be blank")
	private String address;
	
	@NotBlank(message = "City can't be blank")
	private String city;
			
	private Long id;
	private String insuredNumber;
	private double height;
	private double weight;

	public PatientDTO(Patient patient) {
		super();
		this.firstName = patient.getFirstName();
		this.lastName = patient.getLastName();
		this.birthDate = patient.getBirthDate();
		this.gender = patient.getGender();
		this.blodType = patient.getBlodType();
		this.address = patient.getAddress();
		this.city = patient.getCity();
		this.id = patient.getId();
		this.insuredNumber = patient.getInsuredNumber();
		this.height = patient.getHeight();
		this.weight = patient.getWeight();
	}
	
}
