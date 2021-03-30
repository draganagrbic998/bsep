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

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "doctor_alarm_table")
public class DoctorAlarm {
	
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

}
