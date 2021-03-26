package com.example.demo.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dto.MessageDTO;
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

		// on ceratain machines, there's a probability of decimal separator being a comma instead of decimal point
		// 0,54 instead of 0.54

		// therefore, replace all commas in the messageDto

		messageDTO.setText(messageDTO.getText().replace(',', '.'));

		message.setDate(DATE_FORMAT.parse(messageDTO.getText().split(" ")[0].split("=")[1]));
		message.setPulse(Double.parseDouble(messageDTO.getText().split(" ")[2].split("=")[1]));
		message.setPressure(Double.parseDouble(messageDTO.getText().split(" ")[3].split("=")[1]));
		message.setTemperature(Double.parseDouble(messageDTO.getText().split(" ")[4].split("=")[1]));
		message.setOxygenLevel(Double.parseDouble(messageDTO.getText().split(" ")[5].split("=")[1]));
		message.setPatient(this.patientService.findByInsuredNumber(messageDTO.getText().split(" ")[1].split("=")[1]));
		return message;
	}
	
	public List<MessageDTO> map(List<Message> messages){
		return messages.stream().map(MessageDTO::new).collect(Collectors.toList());
	}
	
}
