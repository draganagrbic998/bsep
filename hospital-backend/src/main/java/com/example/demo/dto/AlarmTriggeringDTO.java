package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.AlarmRisk;
import com.example.demo.model.AlarmTriggering;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmTriggeringDTO {

	private long id;
	private AlarmRisk risk;
	private Date date;
	private String message;
	private String patient;
	private String insuredNumber;

	public AlarmTriggeringDTO(AlarmTriggering alarmTriggering) {
		super();
		this.id = alarmTriggering.getId();
		this.risk = alarmTriggering.getRisk();
		this.date = alarmTriggering.getDate();
		this.message = alarmTriggering.getMessage();
		if (alarmTriggering.getPatient() != null) {
			this.patient = alarmTriggering.getPatient().getFirstName() + " " + alarmTriggering.getPatient().getLastName();
			this.insuredNumber = alarmTriggering.getPatient().getInsuredNumber();			
		}
	}
	
}
