package com.example.demo.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.demo.dto.LogDTO;
import com.example.demo.model.Log;
import com.example.demo.model.LogStatus;

@Component
public class LogMapper {

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	
	public Log map(String line) throws ParseException {
		Log log = new Log();
		String[] array = line.split("\\|");
		log.setDate(DATE_FORMAT.parse(array[0].trim()));
		log.setNormal(Boolean.parseBoolean(array[1].trim()));
		log.setStatus(LogStatus.valueOf(array[2].trim().toUpperCase()));
		log.setDescription(array[3].trim());
		log.setUserName(array[4].trim());
		log.setComputerName(array[5].trim());
		log.setServiceName(array[6].trim());
		return log;
	}
	
	public List<LogDTO> map(List<Log> logs){
		return logs.stream().map(LogDTO::new).collect(Collectors.toList());
	}
	
}
