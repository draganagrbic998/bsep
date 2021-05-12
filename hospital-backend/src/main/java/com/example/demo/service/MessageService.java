package com.example.demo.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Message;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.repository.MessageRepository;
import com.example.demo.service.event.MessageEventService;
import com.example.demo.utils.DatabaseCipher;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;
	private final MessageEventService eventService;
	private final DatabaseCipher databaseCipher;
	private final Logger logger;
	
	@Transactional(readOnly = true)
	public Page<Message> findAll(Pageable pageable, String firstName, String lastName, Date date) {
		try {
			Page<Message> response = this.messageRepository.findAll(pageable, 
				this.databaseCipher.encrypt(firstName), 
				this.databaseCipher.encrypt(lastName), 
				date);
			this.logger.write(LogStatus.SUCCESS, String.format("Messages page number %d successfully fetched.", pageable.getPageNumber()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching messages page number %d.", pageable.getPageNumber()));
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public Message save(Message message) {
		try {
			Message response = this.messageRepository.save(message);
			this.eventService.checkAlarms(response);
			this.logger.write(LogStatus.SUCCESS, String.format("Message with id %d successfully saved.", response.getId()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while saving message.");
			throw e;
		}
	}
	
}
