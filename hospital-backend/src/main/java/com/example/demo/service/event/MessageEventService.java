package com.example.demo.service.event;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import com.example.demo.model.Message;
import com.example.demo.service.AlarmTriggeringService;
import com.example.demo.service.DoctorAlarmService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageEventService {

	private final static String DOCTOR_ALARMS = "doctor-alarms";

	private final DoctorAlarmService alarmService;
	private final AlarmTriggeringService alarmTriggeringService;
	private final KieContainer kieContainer;
	
	public void checkAlarms(Message message) {
		KieSession kieSession = this.kieContainer.newKieSession(DOCTOR_ALARMS);
		kieSession.getAgenda().getAgendaGroup(DOCTOR_ALARMS).setFocus();
		kieSession.setGlobal("alarmService", this.alarmService);
		kieSession.setGlobal("alarmTriggeringService", this.alarmTriggeringService);
		kieSession.insert(message);
		kieSession.fireAllRules();
	}
	
}
