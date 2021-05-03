package com.example.demo.service.event;

import javax.annotation.PostConstruct;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import com.example.demo.model.Log;
import com.example.demo.service.AdminAlarmService;
import com.example.demo.service.AlarmTriggeringService;
import com.example.demo.service.MaliciousIpAddressService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogEventService {

	private final static String ADMIN_ALARMS = "admin-alarms";

	private final AdminAlarmService alarmService;
	private final AlarmTriggeringService alarmTriggeringService;
	private final MaliciousIpAddressService ipAddressService;
	private final KieContainer kieContainer;
	
	private static KieSession kieSession;
	
	@PostConstruct
	public void init() {
		kieSession = this.kieContainer.newKieSession(ADMIN_ALARMS);
		kieSession.setGlobal("alarmService", this.alarmService);
		kieSession.setGlobal("alarmTriggeringService", this.alarmTriggeringService);
		kieSession.setGlobal("ipAddressService", this.ipAddressService);
        new Thread(() -> kieSession.fireUntilHalt()).start();
	}

	public void addLog(Log log) {
		kieSession.insert(log);
	}

}
