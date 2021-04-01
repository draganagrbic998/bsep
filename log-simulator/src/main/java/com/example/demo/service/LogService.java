package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Log;
import com.example.demo.repository.LogRepository;

@Service
@Transactional(readOnly = false)
public class LogService {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	private static final long SLEEP_INTERVAL = 5000;
    private static Random RAND = new Random();

	@Autowired
	private LogRepository logRepository;
	
	public Log save(Log log) {
		return this.logRepository.save(log);
	}
	
	public List<Log> findAll(){
		List<Log> logs = this.logRepository.findAll();
		this.logRepository.deleteAll();
		return logs;
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
				String text = String.format("%s|%s|%s|%s|%s|%s|%s", DATE_FORMAT.format(this.getTimestamp()), 
					this.getMode(), this.getStatus(), this.getDescription(), 
					this.getUserName(), this.getComputerName(), this.getServiceName());
				this.logRepository.save(new Log(text));
				Thread.sleep(SLEEP_INTERVAL);
			}
			catch(Exception e) {
				;
			}
		}
	}
	
	private Date getTimestamp() {
		return new Date();
	}

	public String getMode() {
	    List<String> temp = Arrays.asList("true", "false");
	    return temp.get(RAND.nextInt(temp.size()));
	}
	
	public String getStatus() {
	    List<String> temp = Arrays.asList("INFO", "WARNING", "ERROR", "FATAL");
	    return temp.get(RAND.nextInt(temp.size()));
	}
	
	public String getDescription() {
	    List<String> temp = Arrays.asList("Login with wrong credentials", "Forbidden patients view", 
	    	"Forbidden messages view", "Forbidden logs view", "Forbidden alarms view");
	    return temp.get(RAND.nextInt(temp.size()));
	}
	
	public String getUserName() {
	    List<String> temp = Arrays.asList("Djokica", "Perica", "Mikica", "Zikica", "Marica");
	    return temp.get(RAND.nextInt(temp.size()));
	}
	
	public String getComputerName() {
	    List<String> temp = Arrays.asList("Comp1", "Comp2", "Comp3", "Comp4", "Comp5");
	    return temp.get(RAND.nextInt(temp.size()));
	}
	
	public String getServiceName() {
	    List<String> temp = Arrays.asList("UserService", "PatientService", 
	    	"MessageService", "LogService", "AlarmService");
	    return temp.get(RAND.nextInt(temp.size()));
	}
	
}
