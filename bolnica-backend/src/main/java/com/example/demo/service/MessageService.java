package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.MessageSearchDTO;
import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;

@Service
@Transactional(readOnly = true)
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	public Page<Message> findAll(Pageable pageable, MessageSearchDTO searchDTO) {
		return this.messageRepository.findAll(pageable, 
			searchDTO.getInsuredNumber(), 
			searchDTO.getFirstName(), 
			searchDTO.getLastName(), 
			searchDTO.getStartDate(), 
			searchDTO.getEndDate());
	}

	@Transactional(readOnly = false)
	public Message save(Message message) {
		return this.messageRepository.save(message);
	}
	
}
