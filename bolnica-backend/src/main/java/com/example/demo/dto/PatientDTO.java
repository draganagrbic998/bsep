package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.BlodType;
import com.example.demo.model.Gender;
import com.example.demo.model.Patient;

public class PatientDTO {

	private Long id;
	private String insuredNumber;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private Gender gender;
	private BlodType blodType;
	private double height;
	private double weight;
	private String address;
	private String city;
	
	public PatientDTO() {
		super();
	}
	
	public PatientDTO(Patient patient) {
		super();
		this.id = patient.getId();
		this.insuredNumber = patient.getInsuredNumber();
		this.firstName = patient.getFirstName();
		this.lastName = patient.getLastName();
		this.birthDate = patient.getBirthDate();
		this.gender = patient.getGender();
		this.blodType = patient.getBlodType();
		this.height = patient.getHeight();
		this.weight = patient.getWeight();
		this.address = patient.getAddress();
		this.city = patient.getCity();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInsuredNumber() {
		return insuredNumber;
	}

	public void setInsuredNumber(String insuredNumber) {
		this.insuredNumber = insuredNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public BlodType getBlodType() {
		return blodType;
	}

	public void setBlodType(BlodType blodType) {
		this.blodType = blodType;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}