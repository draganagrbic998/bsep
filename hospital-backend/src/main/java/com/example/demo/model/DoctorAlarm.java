package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class DoctorAlarm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="patient_id", foreignKey = @ForeignKey(
	        foreignKeyDefinition = "FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE"))
	private Patient patient;

	private Double minPulse;
	private Double maxPulse;
	private Double minPressure;
	private Double maxPressure;
	private Double minTemperature;
	private Double maxTemperature;
	private Double minOxygenLevel;
	private Double maxOxygenLevel;

}
