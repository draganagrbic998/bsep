package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Message;
import com.example.demo.repository.MessageRepository;

@Service
@Transactional(readOnly = true)
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Transactional(readOnly = false)
	public Message save(Message message) {
		return this.messageRepository.save(message);
	}
	
}
