package com.example.demo.service;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.utils.LogCipher;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogService {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	private static Random RAND = new Random();
	private static final long SLEEP_INTERVAL = 1000;
	private static final String FILE_PATH = "log.txt";
	
	private final LogCipher logCipher;
	
	@PostConstruct
	public void init() {
		new Thread(() -> this.writeLogs()).start();
	}

	private void writeLogs() {
		while (true) {
			try {
				FileWriter writer = new FileWriter(FILE_PATH, true);
				String line = String.format("%s|%s|%s|%s|%s", DATE_FORMAT.format(this.getTimestamp()), this.getMode(),
						this.getStatus(), this.getIpAddress(), this.getDescription());
				writer.write(this.logCipher.encrypt(line) + "\n");
				writer.close();
				Thread.sleep(SLEEP_INTERVAL);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private Date getTimestamp() {
		return new Date();
	}

	public LogMode getMode() {
		return LogMode.values()[RAND.nextInt(LogMode.values().length)];
	}

	public LogStatus getStatus() {
		return LogStatus.values()[RAND.nextInt(LogStatus.values().length)];
	}

	public String getIpAddress() {
		return RAND.nextInt(256) + "." + RAND.nextInt(256) + "." + RAND.nextInt(256) + "." + RAND.nextInt(256);
	}

	public String getDescription() {
		List<String> temp = Arrays.asList("Login with wrong credentials", "Forbidden patients view",
				"Forbidden messages view", "Forbidden logs view", "Forbidden alarms view");
		return temp.get(RAND.nextInt(temp.size()));
	}

}
