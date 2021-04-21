package com.example.demo.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String insuredNumber;

	@NotNull
	private Date birthDate;

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;

	@NotNull
	private Gender gender;

	@NotNull
	private BlodType blodType;

	@NotNull
	private double height;

	@NotNull
	private double weight;

	@NotBlank
	private String address;

	@NotBlank
	private String city;

	public Patient() {
		super();
		this.insuredNumber = UUID.randomUUID().toString();
	}
	
}
