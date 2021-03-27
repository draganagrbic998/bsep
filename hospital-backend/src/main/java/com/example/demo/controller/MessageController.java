package com.example.demo.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MessageDTO;
import com.example.demo.dto.MessageMeasureDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.model.Message;
import com.example.demo.service.MessageService;
import com.example.demo.utils.Constants;

@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MessageMapper messageMapper;

	@PostMapping(value = "/search")
	public ResponseEntity<List<MessageDTO>> findAll(Pageable pageable, @Valid @RequestBody SearchDTO searchDTO, HttpServletResponse response){
		Page<Message> messages = this.messageService.findAll(pageable, searchDTO);
		response.setHeader(Constants.ENABLE_HEADER, Constants.FIRST_PAGE + ", " + Constants.LAST_PAGE);
		response.setHeader(Constants.FIRST_PAGE, messages.isFirst() + "");
		response.setHeader(Constants.LAST_PAGE, messages.isLast() + "");
		return new ResponseEntity<>(this.messageMapper.map(messages.toList()), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody MessageMeasureDTO messageDTO) throws ParseException{
		Message message = this.messageMapper.map(messageDTO);
		if (message.getPatient() != null) {
			this.messageService.save(this.messageMapper.map(messageDTO));			
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
