package com.example.demo.service;

import javax.annotation.PostConstruct;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Log;
import com.example.demo.utils.Constants;

@Service
public class LogEventService {

	@Autowired
	private AdminAlarmService alarmService;
	
	@Autowired
	private AlarmTriggeringService alarmTriggeringService;
	
	@Autowired
	private KieContainer kieContainer;
	
	private KieSession kieSession;
	
	@PostConstruct
	public void init() {
		this.kieSession = this.kieContainer.newKieSession(Constants.ADMIN_ALARMS);
		this.kieSession.setGlobal("alarmService", this.alarmService);
		this.kieSession.setGlobal("alarmTriggeringService", this.alarmTriggeringService);
        new Thread() {
            @Override
            public void run() {
                kieSession.fireUntilHalt();
            }
        }.start();
	}

	public void addLog(Log log) {
		this.kieSession.insert(log);
	}

}
