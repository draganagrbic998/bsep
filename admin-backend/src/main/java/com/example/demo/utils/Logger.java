package com.example.demo.utils;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.model.enums.LogStatus;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Logger {

	public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyyTHH:mm:ss");
	private final static String LOG_FILE = "log.txt";
	private final static String LOG_PATTERN = "%s|%s|%s|%s|%s|%s\n";
	
	private final AuthenticationProvider authProvider;

	public void write(LogStatus status, String message) {
		String service = Thread.currentThread().getStackTrace()[2].getClassName();
		service = service.substring(service.lastIndexOf('.') + 1);
		try {
			FileWriter writer = new FileWriter(LOG_FILE, true);
			writer.write(String.format(LOG_PATTERN,
				DATE_FORMAT.format(new Date()), 
				"NORMAL",
				status,
				this.authProvider.getIpAddress(),
				message,
				service)
			);
			writer.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedDelay = 60000)
	public void rotateLogs() {
		try {
			new FileWriter(LOG_FILE).close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
