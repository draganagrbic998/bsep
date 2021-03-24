package com.example.demo.model;

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
@Table(name = "alarm_table")
public class Alarm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotNull
	@Column(name = "min_pulse")
	private double minPulse;
	
	@NotNull
	@Column(name = "max_pulse")
	private double maxPulse;
	
	@NotNull
	@Column(name = "min_pressure")
	private double minPressure;
	
	@NotNull
	@Column(name = "max_pressure")
	private double maxPressure;
	
	@NotNull
	@Column(name = "min_temperature")
	private double minTemperature;
	
	@NotNull
	@Column(name = "max_temperature")
	private double maxTemperature;
	
	@NotNull
	@Column(name = "min_oxygen_level")
	private double minOxygenLevel;
	
	@NotNull
	@Column(name = "max_oxygen_level")
	private double maxOxygenLevel;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;

	public Alarm() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getMinPulse() {
		return minPulse;
	}

	public void setMinPulse(double minPulse) {
		this.minPulse = minPulse;
	}

	public double getMaxPulse() {
		return maxPulse;
	}

	public void setMaxPulse(double maxPulse) {
		this.maxPulse = maxPulse;
	}

	public double getMinPressure() {
		return minPressure;
	}

	public void setMinPressure(double minPressure) {
		this.minPressure = minPressure;
	}

	public double getMaxPressure() {
		return maxPressure;
	}

	public void setMaxPressure(double maxPressure) {
		this.maxPressure = maxPressure;
	}

	public double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}

	public double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public double getMinOxygenLevel() {
		return minOxygenLevel;
	}

	public void setMinOxygenLevel(double minOxygenLevel) {
		this.minOxygenLevel = minOxygenLevel;
	}

	public double getMaxOxygenLevel() {
		return maxOxygenLevel;
	}

	public void setMaxOxygenLevel(double maxOxygenLevel) {
		this.maxOxygenLevel = maxOxygenLevel;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
}
