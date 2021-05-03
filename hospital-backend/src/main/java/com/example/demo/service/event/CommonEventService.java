package com.example.demo.service.event;

import javax.annotation.PostConstruct;

import org.drools.core.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import com.example.demo.model.Attack;
import com.example.demo.model.InvalidLogin;
import com.example.demo.model.Request;
import com.example.demo.model.enums.LogMode;
import com.example.demo.service.AlarmTriggeringService;
import com.example.demo.service.MaliciousIpAddressService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommonEventService {

	private final static String COMMON_ALARMS = "common-alarms";

	private final AlarmTriggeringService alarmTriggeringService;
	private final MaliciousIpAddressService ipAddressService;
	private final KieContainer kieContainer;
	
	private static KieSession kieSession;

	@PostConstruct
	public void init() {
		kieSession = this.kieContainer.newKieSession(COMMON_ALARMS);
		kieSession.setGlobal("alarmTriggeringService", this.alarmTriggeringService);
		kieSession.setGlobal("ipAddressService", this.ipAddressService);
        new Thread(() -> kieSession.fireUntilHalt()).start();
	}
	
	public void addRequest(Request request) {
		kieSession.insert(request);
	}
	
	public void addInvalidLogin(InvalidLogin invalidLogin) {
		kieSession.insert(invalidLogin);
	}
	
	public static LogMode currentMode() {
		return kieSession.getObjects(new ClassObjectFilter(Attack.class)).size() > 0 ? LogMode.ATTACK : LogMode.NORMAL;
	}

}
