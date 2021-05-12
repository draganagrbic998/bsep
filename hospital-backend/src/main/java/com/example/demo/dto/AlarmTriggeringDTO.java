package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.AlarmTriggering;
import com.example.demo.model.enums.AlarmRisk;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AlarmTriggeringDTO {

	private long id;
	private Date date;
	private AlarmRisk risk;
	private String message;
	private String patient;

	public AlarmTriggeringDTO(AlarmTriggering alarmTriggering) {
		super();
		this.id = alarmTriggering.getId();
		this.date = alarmTriggering.getDate();
		this.risk = alarmTriggering.getRisk();
		this.message = alarmTriggering.getMessage();
		if (alarmTriggering.getPatient() != null)
			this.patient = alarmTriggering.getPatient().getFirstName() + " " + alarmTriggering.getPatient().getLastName();
	}
	
}
