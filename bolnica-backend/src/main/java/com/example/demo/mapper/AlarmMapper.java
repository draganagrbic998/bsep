package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.AlarmDTO;
import com.example.demo.dto.AlarmTriggeringDTO;
import com.example.demo.model.Alarm;
import com.example.demo.model.AlarmTriggering;
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
	
	public List<AlarmTriggeringDTO> map(List<AlarmTriggering> alarmTriggerings){
		return alarmTriggerings.stream().map(AlarmTriggeringDTO::new).collect(Collectors.toList());
	}
	
}
