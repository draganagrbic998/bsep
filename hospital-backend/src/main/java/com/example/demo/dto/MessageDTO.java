package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.Message;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDTO {

	private long id;
	private Date date;
	private double pulse;
	private double pressure;
	private double temperature;
	private double oxygenLevel;
	private String patient;
	private String insuredNumber;
		
	public MessageDTO(Message message) {
		super();
		this.id = message.getId();
		this.date = message.getDate();
		this.pulse = message.getPulse();
		this.pressure = message.getPressure();
		this.temperature = message.getTemperature();
		this.oxygenLevel = message.getOxygenLevel();
		this.patient = message.getPatient().getFirstName() + " " + message.getPatient().getLastName();
		this.insuredNumber = message.getPatient().getInsuredNumber();
	}
	
}
