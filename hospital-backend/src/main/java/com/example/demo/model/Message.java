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

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
	
}
