package com.example.demo.mapper;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.example.demo.dto.LogMeasureDTO;
import com.example.demo.exception.MyException;
import com.example.demo.model.Log;
import com.example.demo.model.LogStatus;

@Component
public class LogMapper {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	
	public Log map(LogMeasureDTO logDTO) {
		try {
			Log log = new Log();
			String[] array = logDTO.getText().split("\\|");
			log.setDate(DATE_FORMAT.parse(array[0].trim()));
			log.setNormal(Boolean.parseBoolean(array[1].trim()));
			log.setStatus(LogStatus.valueOf(array[2].trim().toUpperCase()));
			log.setDescription(array[3].trim());
			log.setUserName(array[4].trim());
			log.setComputerName(array[5].trim());
			log.setServiceName(array[6].trim());
			return log;
		}
		catch(Exception e) {
			throw new MyException();
		}
	}
	
}
