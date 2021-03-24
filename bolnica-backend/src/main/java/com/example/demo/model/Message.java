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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "message_table")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "date")
	private Date date;

	@NotNull
	@Column(name = "pulse")
	private double pulse;

	@NotNull
	@Column(name = "pressure")
	private double pressure;

	@NotNull
	@Column(name = "temperature")
	private double temperature;

	@NotNull
	@Column(name = "oxygen_level")
	private double oxygenLevel;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;
	
	public Message() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPulse() {
		return pulse;
	}

	public void setPulse(double pulse) {
		this.pulse = pulse;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getOxygenLevel() {
		return oxygenLevel;
	}

	public void setOxygenLevel(double oxygenLevel) {
		this.oxygenLevel = oxygenLevel;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
}
