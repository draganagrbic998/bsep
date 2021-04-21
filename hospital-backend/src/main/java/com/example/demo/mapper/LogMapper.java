package com.example.demo.mapper;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.example.demo.model.Log;
import com.example.demo.model.LogMode;
import com.example.demo.model.LogStatus;
import com.example.demo.utils.DatabaseCipher;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LogMapper {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	
	private final DatabaseCipher logCipher;
	
	public Log map(String line, String regExp) {
		try {
			line = this.logCipher.decrypt(line);
			if (!line.matches(regExp))
				return null;
			Log log = new Log();
			line = line.replace(',', '.');
			String[] array = line.split("\\|");
			log.setDate(DATE_FORMAT.parse(array[0].trim()));
			log.setMode(LogMode.valueOf(array[1].trim().toUpperCase()));
			log.setStatus(LogStatus.valueOf(array[2].trim().toUpperCase()));
			log.setIpAddress(array[3].trim());
			log.setDescription(array[4].trim());
			return log;
		}
		catch(Exception e) {
			return null;
		}
	}
	
}
