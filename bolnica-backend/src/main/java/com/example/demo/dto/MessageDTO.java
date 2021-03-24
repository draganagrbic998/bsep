package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.Message;

public class MessageDTO {

	private long id;
	private Date date;
	private double pulse;
	private double pressure;
	private double temperature;
	private double oxygenLevel;
	private String patient;
	
	public MessageDTO() {
		super();
	}
	
	public MessageDTO(Message message) {
		super();
		this.id = message.getId();
		this.date = message.getDate();
		this.pulse = message.getPulse();
		this.pressure = message.getPressure();
		this.temperature = message.getTemperature();
		this.oxygenLevel = message.getOxygenLevel();
		this.patient = message.getPatient().getFirstName() + " " + message.getPatient().getLastName();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}
	
}
