package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.AlarmTriggering;

public class AlarmTriggeringDTO {

	private long id;
	private Date date;
	private String message;
	private String patient;
	private String insuredNumber;
	
	public AlarmTriggeringDTO() {
		super();
	}
	
	public AlarmTriggeringDTO(AlarmTriggering alarmTriggering) {
		super();
		this.id = alarmTriggering.getId();
		this.date = alarmTriggering.getDate();
		this.message = alarmTriggering.getMessage();
		if (alarmTriggering.getPatient() != null) {
			this.patient = alarmTriggering.getPatient().getFirstName() + " " + alarmTriggering.getPatient().getLastName();
			this.insuredNumber = alarmTriggering.getPatient().getInsuredNumber();			
		}
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getInsuredNumber() {
		return insuredNumber;
	}

	public void setInsuredNumber(String insuredNumber) {
		this.insuredNumber = insuredNumber;
	}
	
}