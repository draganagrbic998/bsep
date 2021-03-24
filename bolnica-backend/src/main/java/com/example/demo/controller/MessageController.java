package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MessageDTO;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.service.MessageService;

@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private MessageMapper messageMapper;
	
	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody MessageDTO messageDTO){
		this.messageService.save(this.messageMapper.map(messageDTO));
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
