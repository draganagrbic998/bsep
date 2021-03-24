package com.example.demo.mapper;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.MessageDTO;
import com.example.demo.model.Message;
import com.example.demo.service.PatientService;

@Component
public class MessageMapper {

	@Autowired
	private PatientService patientService;
	
	public Message map(MessageDTO messageDTO) {
		Message message = new Message();
		message.setDate(LocalDate.parse(messageDTO.getText().split(" ")[0].split("=")[1]));
		message.setPulse(Double.parseDouble(messageDTO.getText().split(" ")[2].split("=")[1]));
		message.setPressure(Double.parseDouble(messageDTO.getText().split(" ")[3].split("=")[1]));
		message.setTemperature(Double.parseDouble(messageDTO.getText().split(" ")[4].split("=")[1]));
		message.setOxygenLevel(Double.parseDouble(messageDTO.getText().split(" ")[5].split("=")[1]));
		message.setPatient(this.patientService.find(messageDTO.getText().split(" ")[1].split("=")[1]));
		return message;
	}
	
}
