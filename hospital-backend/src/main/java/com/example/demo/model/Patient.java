package com.example.demo.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "patient_table")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank
	@Column(name = "insured_number", unique = true)
	private String insuredNumber;

	@NotBlank
	@Column(name = "first_name")
	private String firstName;
	
	@NotBlank
	@Column(name = "last_name")
	private String lastName;

	@NotNull
	@Column(name = "birth_date")
	private LocalDate birthDate;

	@NotNull
	@Column(name = "gender")
	private Gender gender;

	@NotNull
	@Column(name = "blod_type")
	private BlodType blodType;

	@NotNull
	@Column(name = "height")
	private double height;

	@NotNull
	@Column(name = "weight")
	private double weight;

	@NotBlank
	@Column(name = "address")
	private String address;

	@NotBlank
	@Column(name = "city")
	private String city;

	public Patient() {
		super();
		this.insuredNumber = UUID.randomUUID().toString();
	}
	
}
