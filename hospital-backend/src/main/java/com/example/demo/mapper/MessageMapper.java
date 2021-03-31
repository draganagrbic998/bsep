package com.example.demo.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.MessageMeasureDTO;
import com.example.demo.model.Message;
import com.example.demo.service.PatientService;

@Component
public class MessageMapper {

	@Autowired
	private PatientService patientService;
	
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	
	public Message map(MessageMeasureDTO messageDTO) throws ParseException {
		Message message = new Message();
		messageDTO.setText(messageDTO.getText().replace(',', '.'));
		String[] array = messageDTO.getText().split(" ");
		message.setDate(DATE_FORMAT.parse(array[0].split("=")[1].trim()));
		message.setPatient(this.patientService.find(Long.parseLong(array[1].trim().split("=")[1].trim())));
		message.setPulse(Double.parseDouble(array[2].split("=")[1].trim()));
		message.setPressure(Double.parseDouble(array[3].split("=")[1].trim()));
		message.setTemperature(Double.parseDouble(array[4].split("=")[1].trim()));
		message.setOxygenLevel(Double.parseDouble(array[5].split("=")[1].trim()));
		return message;
	}
		
}
