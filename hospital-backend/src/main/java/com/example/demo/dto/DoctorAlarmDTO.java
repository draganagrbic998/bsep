package com.example.demo.dto;

import com.example.demo.model.DoctorAlarm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DoctorAlarmDTO {

	private Long id;
	private Double minPulse;
	private Double maxPulse;
	private Double minPressure;
	private Double maxPressure;
	private Double minTemperature;
	private Double maxTemperature;
	private Double minOxygenLevel;
	private Double maxOxygenLevel;
	
	public DoctorAlarmDTO(DoctorAlarm alarm) {
		super();
		this.id = alarm.getId();
		this.minPulse = alarm.getMinPulse();
		this.maxPulse = alarm.getMaxPulse();
		this.minPressure = alarm.getMinPressure();
		this.maxPressure = alarm.getMaxPressure();
		this.minTemperature = alarm.getMinTemperature();
		this.maxTemperature = alarm.getMaxTemperature();
		this.minOxygenLevel = alarm.getMinOxygenLevel();
		this.maxOxygenLevel = alarm.getMaxOxygenLevel();
	}

}
