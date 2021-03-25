package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AlarmDTO;
import com.example.demo.dto.AlarmTriggeringDTO;
import com.example.demo.dto.SearchDTO;
import com.example.demo.mapper.AlarmMapper;
import com.example.demo.model.AlarmTriggering;
import com.example.demo.service.AlarmService;
import com.example.demo.service.AlarmTriggeringService;
import com.example.demo.utils.Constants;

@RestController
@RequestMapping(value = "/api/alarms", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlarmController {

	@Autowired
	private AlarmService alarmService;
	
	@Autowired
	private AlarmTriggeringService alarmTriggeringService;
	
	@Autowired
	private AlarmMapper alarmMapper;
	
	@PostMapping(value = "/search")
	public ResponseEntity<List<AlarmTriggeringDTO>> findAll(Pageable pageable, @Valid @RequestBody SearchDTO searchDTO, HttpServletResponse response){
		Page<AlarmTriggering> alarms = this.alarmTriggeringService.findAll(pageable, searchDTO);
		response.setHeader(Constants.ENABLE_HEADER, Constants.FIRST_PAGE + ", " + Constants.LAST_PAGE);
		response.setHeader(Constants.FIRST_PAGE, alarms.isFirst() + "");
		response.setHeader(Constants.LAST_PAGE, alarms.isLast() + "");
		return new ResponseEntity<>(this.alarmMapper.map(alarms.toList()), HttpStatus.OK);
	}

	@PostMapping(value = "/{patientId}")
	public ResponseEntity<AlarmDTO> create(@PathVariable long patientId, @Valid @RequestBody AlarmDTO alarmDTO){
		this.alarmService.save(this.alarmMapper.map(patientId, alarmDTO));
		return new ResponseEntity<>(alarmDTO, HttpStatus.CREATED);
	}
	
}
