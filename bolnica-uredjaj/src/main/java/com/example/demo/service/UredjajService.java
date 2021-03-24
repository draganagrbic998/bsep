package com.example.demo.service;

import java.time.LocalDate;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.MessageDTO;

@Service
public class UredjajService {

	private static final String MESSAGES_API = "http://localhost:8081/api/messages";
	private static final long SLEEP_INTERVAL = 5000;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostConstruct
	public void init() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					monitorPatients();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	private void monitorPatients() throws InterruptedException {
		while (true) {
			String text = String.format("Timestamp=%s patient=%s pusle=%.2f pressure=%.2f temperature=%.2f oxygen_level=%.2f", 
					LocalDate.now(), this.getPatient(), this.getPulse(), this.getPressure(), this.getTemperature(), this.getOxygenLevel());
			System.out.println(text);
			this.restTemplate.postForEntity(MESSAGES_API, new MessageDTO(text), String.class);
			Thread.sleep(SLEEP_INTERVAL);
		}
	}
	
	private String getPatient() {
		return UUID.randomUUID().toString();
	}
	
	private double getPulse() {
		return Math.random();
	}
	
	private double getPressure() {
		return Math.random();
	}
	
	private double getTemperature() {
		return Math.random();
	}
	
	private double getOxygenLevel() {
		return Math.random();
	}
	
}
