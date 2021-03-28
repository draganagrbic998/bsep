package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.DoctorAlarmDTO;
import com.example.demo.dto.AdminAlarmDTO;
import com.example.demo.dto.AlarmTriggeringDTO;
import com.example.demo.model.DoctorAlarm;
import com.example.demo.model.AdminAlarm;
import com.example.demo.model.AlarmTriggering;
import com.example.demo.service.PatientService;

@Component
public class AlarmMapper {
	
	@Autowired
	private PatientService patientService;

	public DoctorAlarm map(long patientId, DoctorAlarmDTO alarmDTO) {
		DoctorAlarm alarm = new DoctorAlarm();
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
	
	public AdminAlarm map(AdminAlarmDTO alarmDTO) {
		AdminAlarm alarm = new AdminAlarm();
		alarm.setStatus(alarmDTO.isStatus());
		alarm.setParam(alarmDTO.getParam());
		alarm.setCounts(alarmDTO.getCounts());
		return alarm;
	}

	public List<DoctorAlarmDTO> doctorMap(List<DoctorAlarm> alarms){
		return alarms.stream().map(DoctorAlarmDTO::new).collect(Collectors.toList());
	}

	public List<AdminAlarmDTO> adminMap(List<AdminAlarm> alarms){
		return alarms.stream().map(AdminAlarmDTO::new).collect(Collectors.toList());
	}

	public List<AlarmTriggeringDTO> triggeringsMap(List<AlarmTriggering> alarmTriggerings){
		return alarmTriggerings.stream().map(AlarmTriggeringDTO::new).collect(Collectors.toList());
	}
	
}
