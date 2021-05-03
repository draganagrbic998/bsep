package com.example.demo.mapper;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.example.demo.dto.MessageDTO;
import com.example.demo.dto.MessageMeasureDTO;
import com.example.demo.model.Message;
import com.example.demo.service.PatientService;
import com.example.demo.utils.DatabaseCipher;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MessageMapper {

	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	
	private final PatientService patientService;
	private final DatabaseCipher databaseCipher;

	public Message map(MessageMeasureDTO messageDTO) {
		try {
			Message message = new Message();
			String[] array = messageDTO.getText().replace(',', '.').split(" ");
			message.setDate(DATE_FORMAT.parse(array[0].trim().split("=")[1].trim()));
			message.setPatient(this.patientService.findOne(Long.parseLong(array[1].trim().split("=")[1].trim())));
			message.setPulse(Double.parseDouble(array[2].trim().split("=")[1].trim()));
			message.setPressure(Double.parseDouble(array[3].trim().split("=")[1].trim()));
			message.setTemperature(Double.parseDouble(array[4].trim().split("=")[1].trim()));
			message.setOxygenLevel(Double.parseDouble(array[5].trim().split("=")[1].trim()));
			return message;
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public MessageDTO map(Message message) {
		return new MessageDTO(this.databaseCipher.decrypt(message));
	}
		
}
