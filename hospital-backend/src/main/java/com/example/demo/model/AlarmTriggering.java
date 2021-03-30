package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "alarm_triggering_table")
public class AlarmTriggering {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "date")
	private Date date;

	@NotBlank
	@Column(name = "message")
	private String message;
	
	@ManyToOne
	@JoinColumn(name="patient_id")
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
