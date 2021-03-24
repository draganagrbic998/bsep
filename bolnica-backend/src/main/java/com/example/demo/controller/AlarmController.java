package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AlarmDTO;
import com.example.demo.mapper.AlarmMapper;
import com.example.demo.service.AlarmService;

@RestController
@RequestMapping(value = "/api/alarms", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlarmController {

	@Autowired
	private AlarmService alarmService;
	
	@Autowired
	private AlarmMapper alarmMapper;
	
	@PostMapping(value = "/{patientId}")
	public ResponseEntity<AlarmDTO> create(@PathVariable long patientId, @Valid @RequestBody AlarmDTO alarmDTO){
		this.alarmService.save(this.alarmMapper.map(patientId, alarmDTO));
		return new ResponseEntity<>(alarmDTO, HttpStatus.CREATED);
	}
	
}
