package com.example.demo.service;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Message;
import com.example.demo.utils.Constants;

@Service
public class EventService {

	@Autowired
	private AlarmTriggeringService alarmTriggeringService;
	
	@Autowired
	private KieContainer kieContainer;
	
	public void checkAlarm(Message message) {
		KieSession kieSession = this.kieContainer.newKieSession(Constants.ALARM_RULES);
		kieSession.getAgenda().getAgendaGroup(Constants.ALARM_RULES).setFocus();
		kieSession.setGlobal("alarmService", this.alarmTriggeringService);
		kieSession.insert(message);
		kieSession.fireAllRules();
	}
	
}
