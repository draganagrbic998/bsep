package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.dto.AlarmTriggeringDTO;
import com.example.demo.model.AlarmTriggering;
import com.example.demo.utils.DatabaseCipher;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AlarmTriggeringMapper {

	private final DatabaseCipher databaseCipher;
	
	public AlarmTriggeringDTO map(AlarmTriggering alarmTriggering) {
		return new AlarmTriggeringDTO(this.databaseCipher.decrypt(alarmTriggering));
	}

}
