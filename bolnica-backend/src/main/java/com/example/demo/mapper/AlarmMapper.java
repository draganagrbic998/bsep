package com.example.demo.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.AlarmDTO;
import com.example.demo.model.Alarm;
import com.example.demo.service.PatientService;

@Component
public class AlarmMapper {
	
	@Autowired
	private PatientService patientService;

	public Alarm map(long patientId, AlarmDTO alarmDTO) {
		Alarm alarm = new Alarm();
		alarm.setPatient(this.patientService.find(patientId));
		alarm.setMinPulse(alarmDTO.getMinPulse());
		alarm.setMaxPulse(alarmDTO.getMaxPulse());
		alarm.setMinPressure(alarmDTO.getMinPressure());
		alarm.setMaxPressure(alarmDTO.getMaxPressure());
		alarm.setMinTemperature(alarmDTO.getMinTemperature());
		alarm.setMaxTemperature(alarmDTO.getMaxTemperature());
		alarm.setMinOxygenLevel(alarmDTO.getMinOxygenLevel());
		alarm.setMaxOxygenLevel(alarmDTO.getMaxOxygenLevel());
		return alarm;
	}
	
}