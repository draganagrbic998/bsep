package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.demo.model.enums.AlarmRisk;
import com.example.demo.model.enums.AlarmType;

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

	@NotNull
	@Enumerated(EnumType.STRING)
	private AlarmType type;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private AlarmRisk risk;

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
	
	public AlarmTriggering(AlarmRisk risk, String message) {
		this();
		this.type = AlarmType.LOG;
		this.risk = risk;
		this.message = message;
	}

	public AlarmTriggering(AlarmRisk risk, Patient patient, String message) {
		this();
		this.type = AlarmType.PATIENT;
		this.risk = risk;
		this.patient = patient;
		this.message = message;
	}

	public AlarmTriggering(AlarmType type, AlarmRisk risk, String message) {
		this();
		this.type = type;
		this.risk = risk;
		this.message = message;
	}

}
