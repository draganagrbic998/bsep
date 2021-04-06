package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.MessageSearchDTO;
import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
	private final MessageEventService eventService;
	
	public Page<Message> findAll(Pageable pageable, MessageSearchDTO searchDTO) {
		return this.messageRepository.findAll(pageable, 
			searchDTO.getInsuredNumber(), 
			searchDTO.getFirstName(), 
			searchDTO.getLastName(), 
			searchDTO.getDate());
	}

	@Transactional(readOnly = false)
	public Message save(Message message) {
		message = this.messageRepository.save(message);
		this.eventService.checkAlarms(message);
		return message;
	}
	
}
