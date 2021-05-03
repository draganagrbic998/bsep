package com.example.demo.mapper;

import java.text.ParseException;
import java.util.Date;
import java.util.function.Function;

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
		Log log = new Log();
		String[] array = line.replace(',', '.').split("\\|");
		log.setDate((Date) this.parse(array, param -> {
			try {
				return Logger.DATE_FORMAT.parse(param.trim());
			} 
			catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}));
		log.setMode((LogMode) this.parse(array, param -> LogMode.valueOf(param.trim().toUpperCase())));
		log.setStatus((LogStatus) this.parse(array, param -> LogStatus.valueOf(param.trim().toUpperCase())));
		log.setIpAddress((String) this.parse(array, param -> {
			if (param.split("\\.").length != 4)
				throw new RuntimeException();
			return param.trim();
		}));
		log.setDescription((String) this.parse(array, param -> param.trim()));
		return log;
	}
	
	private Object parse(String[] array, Function<String, Object> function) {
		for (int i = 0; i < array.length; ++i) {
			if (array[i] != null) {
				try {
					Object response = function.apply(array[i]);
					array[i] = null;
					return response;
				}
				catch(Exception e) {}				
			}
		}
		return null;
	}
	
}
