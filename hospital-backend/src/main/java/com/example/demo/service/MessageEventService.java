package com.example.demo.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Message;
import com.example.demo.utils.Constants;

@Service
public class MessageEventService {

	@Autowired
	private DoctorAlarmService alarmService;
	
	@Autowired
	private AlarmTriggeringService alarmTriggeringService;
	
	@Autowired
	private KieContainer kieContainer;
	
	public void checkAlarm(Message message) {
		KieSession kieSession = this.kieContainer.newKieSession(Constants.DOCTOR_ALARMS);
		kieSession.getAgenda().getAgendaGroup(Constants.DOCTOR_ALARMS).setFocus();
		kieSession.setGlobal("alarmService", this.alarmService);
		kieSession.setGlobal("alarmTriggeringService", this.alarmTriggeringService);
		kieSession.insert(message);
		kieSession.fireAllRules();
	}
	
}
