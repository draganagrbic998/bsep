package com.example.demo.service;

import javax.annotation.PostConstruct;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.InvalidLogin;
import com.example.demo.model.Request;
import com.example.demo.utils.Constants;

@Service
public class CommonEventService {

	@Autowired
	private AlarmTriggeringService alarmTriggeringService;
	
	@Autowired
	private MaliciousIpAddressService ipAddressService;
	
	@Autowired
	private KieContainer kieContainer;
	
	private KieSession kieSession;

	@PostConstruct
	public void init() {
		this.kieSession = this.kieContainer.newKieSession(Constants.COMMON_ALARMS);
		this.kieSession.setGlobal("alarmTriggeringService", this.alarmTriggeringService);
		this.kieSession.setGlobal("ipAddressService", this.ipAddressService);
        new Thread(() -> this.kieSession.fireUntilHalt()).start();
	}
	
	public void addRequest(Request request) {
		this.kieSession.insert(request);
	}
	
	public void addInvalidLogin(InvalidLogin invalidLogin) {
		this.kieSession.insert(invalidLogin);
	}

}
