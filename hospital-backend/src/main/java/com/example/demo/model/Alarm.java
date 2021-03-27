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

	@Column(name = "min_pulse")
	private Double minPulse;
	
	@Column(name = "max_pulse")
	private Double maxPulse;
	
	@Column(name = "min_pressure")
	private Double minPressure;
	
	@Column(name = "max_pressure")
	private Double maxPressure;
	
	@Column(name = "min_temperature")
	private Double minTemperature;
	
	@Column(name = "max_temperature")
	private Double maxTemperature;
	
	@Column(name = "min_oxygen_level")
	private Double minOxygenLevel;
	
	@Column(name = "max_oxygen_level")
	private Double maxOxygenLevel;
	
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

	public Double getMinPulse() {
		return minPulse;
	}

	public void setMinPulse(Double minPulse) {
		this.minPulse = minPulse;
	}

	public Double getMaxPulse() {
		return maxPulse;
	}

	public void setMaxPulse(Double maxPulse) {
		this.maxPulse = maxPulse;
	}

	public Double getMinPressure() {
		return minPressure;
	}

	public void setMinPressure(Double minPressure) {
		this.minPressure = minPressure;
	}

	public Double getMaxPressure() {
		return maxPressure;
	}

	public void setMaxPressure(Double maxPressure) {
		this.maxPressure = maxPressure;
	}

	public Double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(Double minTemperature) {
		this.minTemperature = minTemperature;
	}

	public Double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(Double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public Double getMinOxygenLevel() {
		return minOxygenLevel;
	}

	public void setMinOxygenLevel(Double minOxygenLevel) {
		this.minOxygenLevel = minOxygenLevel;
	}

	public Double getMaxOxygenLevel() {
		return maxOxygenLevel;
	}

	public void setMaxOxygenLevel(Double maxOxygenLevel) {
		this.maxOxygenLevel = maxOxygenLevel;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
}
