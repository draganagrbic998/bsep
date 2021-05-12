package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.DoctorAlarmDTO;
import com.example.demo.dto.AdminAlarmDTO;
import com.example.demo.model.DoctorAlarm;
import com.example.demo.model.AdminAlarm;
import com.example.demo.service.PatientService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AlarmMapper {
	
	private final PatientService patientService;
	
	public AdminAlarm map(AdminAlarmDTO alarmDTO) {
		AdminAlarm alarm = new AdminAlarm();
		alarm.setStatus(alarmDTO.getStatus());
		alarm.setService(alarmDTO.getService());
		alarm.setCounts(alarmDTO.getCounts());
		return alarm;
	}

	public DoctorAlarm map(long patientId, DoctorAlarmDTO alarmDTO) {
		DoctorAlarm alarm = new DoctorAlarm();
		alarm.setMinPulse(alarmDTO.getMinPulse());
		alarm.setMaxPulse(alarmDTO.getMaxPulse());
		alarm.setMinPressure(alarmDTO.getMinPressure());
		alarm.setMaxPressure(alarmDTO.getMaxPressure());
		alarm.setMinTemperature(alarmDTO.getMinTemperature());
		alarm.setMaxTemperature(alarmDTO.getMaxTemperature());
		alarm.setMinOxygenLevel(alarmDTO.getMinOxygenLevel());
		alarm.setMaxOxygenLevel(alarmDTO.getMaxOxygenLevel());
		alarm.setPatient(this.patientService.findOne(patientId));
		return alarm;
	}
	
}
