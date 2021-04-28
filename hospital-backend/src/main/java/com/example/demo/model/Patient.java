package com.example.demo.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@NotNull
	@Enumerated(EnumType.STRING)
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
