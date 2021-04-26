package com.example.demo.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.example.demo.dto.AlarmTriggeringDTO;
import com.example.demo.model.AlarmTriggering;
import com.example.demo.utils.DatabaseCipher;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AlarmTriggeringMapper {

	private final DatabaseCipher databaseCipher;

	public Page<AlarmTriggeringDTO> map(Page<AlarmTriggering> alarmTriggerings) {
		return alarmTriggerings.map(alarmTriggering -> new AlarmTriggeringDTO(this.databaseCipher.decrypt(alarmTriggering)));
	}

}
