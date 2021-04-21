package com.example.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.persistence.ForeignKey;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private Date date;

	@NotNull
	private double pulse;

	@NotNull
	private double pressure;

	@NotNull
	private double temperature;

	@NotNull
	private double oxygenLevel;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="patient_id", foreignKey = @ForeignKey(
        foreignKeyDefinition = "FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE"))
	private Patient patient;
	
}
