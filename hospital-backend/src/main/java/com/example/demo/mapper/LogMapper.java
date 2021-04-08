package com.example.demo.mapper;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.example.demo.dto.LogMeasureDTO;
import com.example.demo.model.Log;
import com.example.demo.model.LogMode;
import com.example.demo.model.LogStatus;

@Component
public class LogMapper {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	
	public Log map(LogMeasureDTO logDTO) {
		try {
			Log log = new Log();
			logDTO.setText(logDTO.getText().replace(',', '.'));
			String[] array = logDTO.getText().split("\\|");
			log.setDate(DATE_FORMAT.parse(array[0].trim()));
			log.setMode(LogMode.valueOf(array[1].trim().toUpperCase()));
			log.setStatus(LogStatus.valueOf(array[2].trim().toUpperCase()));
			log.setIpAddress(array[3].trim());
			log.setDescription(array[4].trim());
			return log;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
