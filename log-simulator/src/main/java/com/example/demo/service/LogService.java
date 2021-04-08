package com.example.demo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.example.demo.dto.LogDTO;

@Service
public class LogService {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	private static Random RAND = new Random();
	private static final long SLEEP_INTERVAL = 5000;
    private static final String FILE_PATH = "log.txt";
		
	public List<LogDTO> findAll() {
		try {
			List<LogDTO> logsDTO = new ArrayList<>();
			BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
			String line;
			while ((line = reader.readLine()) != null) {
				logsDTO.add(new LogDTO(line));
			}
			reader.close();
			FileWriter writer = new FileWriter(FILE_PATH);
			writer.close();
			return logsDTO;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

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
				String line = String.format("%s|%s|%s|%s|%s", DATE_FORMAT.format(this.getTimestamp()), 
					this.getMode(), this.getStatus(), this.getIpAddress(), this.getDescription());
				writer.println(line);
				writer.close();
				Thread.sleep(SLEEP_INTERVAL);
			}
			catch(Exception e) {
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
