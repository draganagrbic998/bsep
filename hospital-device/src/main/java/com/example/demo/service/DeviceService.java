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

	private final static String MESSAGES_API = "https://localhost:8081/api/messages/save";
	private final static Random RANDOM = new Random();
	
	private final RestTemplate restTemplate;
	
	@PostConstruct
	public void init() {
		new Thread(() -> this.monitorPatients()).start();
	}
	
	private void monitorPatients() {
		while (true) {
			try {
				String text = String.format("Timestamp=%s patient=%d pulse=%.2f pressure=%.2f temperature=%.2f oxygen_level=%.2f", 
					new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss").format(new Date()), 1, 
					this.getPulse(), this.getPressure(), this.getTemperature(), this.getOxygenLevel());
				this.restTemplate.postForEntity(MESSAGES_API, new MessageDTO(text), String.class);
				Thread.sleep(5000);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
			
	private double getPulse() {
		return 40 + (120 - 40) * RANDOM.nextDouble();
	}
	
	private double getPressure() {
		return 80 + (200 - 80) * RANDOM.nextDouble();
	}
	
	private double getTemperature() {
		return 30 + (45 - 30) * RANDOM.nextDouble();
	}
	
	private double getOxygenLevel() {
		return 80 + (100 - 80) * RANDOM.nextDouble();
	}
	
}
