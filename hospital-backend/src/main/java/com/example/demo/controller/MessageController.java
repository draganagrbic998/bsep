package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.MessageDTO;
import com.example.demo.dto.MessageMeasureDTO;
import com.example.demo.dto.MessageSearchDTO;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.model.Message;
import com.example.demo.service.MessageService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/messages", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class MessageController {

	private final MessageService messageService;
	private final MessageMapper messageMapper;

	@PreAuthorize("hasAuthority('DOCTOR')")
	@PostMapping(value = "/search")
	public ResponseEntity<Page<MessageDTO>> findAll(Pageable pageable, @Valid @RequestBody MessageSearchDTO searchDTO) {
		return ResponseEntity.ok(this.messageService.findAll(pageable, searchDTO).map(MessageDTO::new));
	}

	@PostMapping
	public ResponseEntity<MessageMeasureDTO> create(@Valid @RequestBody MessageMeasureDTO messageDTO) {
		Message message = this.messageMapper.map(messageDTO);
		if (message.getPatient() != null) {
			this.messageService.save(this.messageMapper.map(messageDTO));
		}
		return ResponseEntity.ok(messageDTO);
	}

}
