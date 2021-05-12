package com.example.demo.service;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.model.LogMode;
import com.example.demo.model.LogStatus;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogService {

	private final static String LOG_FILE = "log.txt";
	private final static Random RANDOM = new Random();
		
	@PostConstruct
	public void init() {
		new Thread(() -> this.writeLogs()).start();
	}

	private void writeLogs() {
		while (true) {
			try {
				FileWriter writer = new FileWriter(LOG_FILE, true);
				String line = String.format("%s|%s|%s|%s|%s|%s\n", 
					new SimpleDateFormat("dd/MM/yyyyTHH:mm:ss").format(new Date()), 
					this.getMode(), this.getStatus(), this.getIpAddress(), this.getDescription(), this.getService());
				writer.write(line);
				writer.close();
				Thread.sleep(1000);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public LogMode getMode() {
		return LogMode.ATTACK;
		//return LogMode.values()[RANDOM.nextInt(LogMode.values().length)];
	}

	public LogStatus getStatus() {
		return LogStatus.values()[RANDOM.nextInt(LogStatus.values().length)];
	}

	public String getIpAddress() {
		return RANDOM.nextInt(256) + "." + RANDOM.nextInt(256) + "." + RANDOM.nextInt(256) + "." + RANDOM.nextInt(256);
	}

	public String getDescription() {
		List<String> temp = Arrays.asList("Description one", "Description two", "Description three", "Description four", "Description five");
		return temp.get(RANDOM.nextInt(temp.size()));
	}
	
	public String getService() {
		List<String> temp = Arrays.asList("AdminAlarmService", "AlarmTriggeringService", "CertificateService", "ConfigurationService", 
			"DoctorAlarmService", "LogService", "MessageService", "PatientService", "ReportService", "UserService");
		return temp.get(RANDOM.nextInt(temp.size()));
	}

}
