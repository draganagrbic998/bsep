package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class AlarmTriggering {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private Date date;

	@NotBlank
	private String message;
	
	@ManyToOne
	@JoinColumn(name="patient_id", foreignKey = @ForeignKey(
	        foreignKeyDefinition = "FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE"))
	private Patient patient;

	public AlarmTriggering() {
		super();
		this.date = new Date();
	}
	
	public AlarmTriggering(String message) {
		this();
		this.message = message;
	}

	public AlarmTriggering(Patient patient, String message) {
		this();
		this.patient = patient;
		this.message = message;
	}

}
