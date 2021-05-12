package com.example.demo.utils;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.demo.model.enums.LogStatus;
import com.example.demo.service.event.CommonEventService;

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
				CommonEventService.currentMode(),
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
	
}
