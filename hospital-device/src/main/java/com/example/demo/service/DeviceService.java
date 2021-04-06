package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.MessageDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeviceService {

	private static final String MESSAGES_API = "https://localhost:8081/api/messages";
	private static final Random RANDOM = new Random();
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	private static final long SLEEP_INTERVAL = 5000;
	private static final long PATIENT_ID = 1;
	
	private final RestTemplate restTemplate;
	
	@PostConstruct
	public void init() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				monitorPatients();
			}
		}).start();
	}
	
	private void monitorPatients() {
		while (true) {
			try {
				String text = String.format("Timestamp=%s patient=%d pulse=%.2f pressure=%.2f temperature=%.2f oxygen_level=%.2f", 
						DATE_FORMAT.format(this.getTimestamp()), PATIENT_ID, this.getPulse(), this.getPressure(), this.getTemperature(), this.getOxygenLevel());
				this.restTemplate.postForEntity(MESSAGES_API, new MessageDTO(text), String.class);
				Thread.sleep(SLEEP_INTERVAL);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private Date getTimestamp() {
		return new Date();
	}
		
	private double getPulse() {
		return Math.random();
	}
	
	private double getPressure() {
		return Math.random();
	}
	
	private double getTemperature() {
		return 30 + (45 - 35) * RANDOM.nextDouble();
	}
	
	private double getOxygenLevel() {
		return Math.random();
	}
	
}
