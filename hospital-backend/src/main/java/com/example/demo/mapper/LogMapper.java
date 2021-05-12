package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.model.Log;
import com.example.demo.model.enums.LogMode;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LogMapper {
		
	public Log map(String line) {
		try {
			Log log = new Log();
			String[] array = line.replace(',', '.').split("\\|");
			log.setDate(Logger.DATE_FORMAT.parse(array[0].trim()));
			log.setMode(LogMode.valueOf(array[1].toUpperCase().trim()));
			log.setStatus(LogStatus.valueOf(array[2].toUpperCase().trim()));
			log.setIpAddress(array[3].trim());
			log.setDescription(array[4].trim());
			log.setService(array[5].trim());
			return log;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
