package com.example.demo.service;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.utils.LogCipher;

@Service
public class LogService {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	private static Random RAND = new Random();
	private static final long SLEEP_INTERVAL = 5000;
	private static final String FILE_PATH = "log.txt";
	
	@Autowired
	private LogCipher logCipher;
	
	@PostConstruct
	public void init() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				generateLogs();
			}
		}).start();
	}

	private void generateLogs() {
		while (true) {
			try {
				PrintWriter writer = new PrintWriter(FILE_PATH);
				String line = String.format("%s|%s|%s|%s|%s", DATE_FORMAT.format(this.getTimestamp()), this.getMode(),
						this.getStatus(), this.getIpAddress(), this.getDescription());
				writer.println(this.logCipher.encrypt(line));
				writer.close();
				Thread.sleep(SLEEP_INTERVAL);
			} catch (Exception e) {
				throw new RuntimeException(e);
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
