package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AlarmTriggeringDTO;
import com.example.demo.mapper.AlarmMapper;
import com.example.demo.model.AlarmTriggering;
import com.example.demo.service.AlarmTriggeringService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Constants;

@RestController
@RequestMapping(value = "/api/alarm-triggerings", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('ADMIN','DOCTOR')")
public class AlarmTriggeringController {

	@Autowired
	private UserService userService;

	@Autowired
	private AlarmTriggeringService alarmTriggeringService;

	@Autowired
	private AlarmMapper alarmMapper;
	
	@GetMapping
	public ResponseEntity<List<AlarmTriggeringDTO>> findAll(Pageable pageable, HttpServletResponse response){
		Page<AlarmTriggering> alarms;
		if (this.userService.currentUser().isAdmin()) {
			alarms = this.alarmTriggeringService.findAllForAdmin(pageable);
		}
		else {
			alarms = this.alarmTriggeringService.findAllForDoctor(pageable);
		}
		response.setHeader(Constants.ENABLE_HEADER, Constants.FIRST_PAGE + ", " + Constants.LAST_PAGE);
		response.setHeader(Constants.FIRST_PAGE, alarms.isFirst() + "");
		response.setHeader(Constants.LAST_PAGE, alarms.isLast() + "");
		return ResponseEntity.ok(this.alarmMapper.triggeringsMap(alarms.toList()));
	}
	
}
