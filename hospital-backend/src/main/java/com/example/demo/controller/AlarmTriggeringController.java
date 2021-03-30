package com.example.demo.controller;

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
import com.example.demo.service.AlarmTriggeringService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping(value = "/api/alarm-triggerings", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('ADMIN','DOCTOR')")
public class AlarmTriggeringController {

	@Autowired
	private UserService userService;

	@Autowired
	private AlarmTriggeringService alarmTriggeringService;
	
	@GetMapping
	public ResponseEntity<Page<AlarmTriggeringDTO>> findAll(Pageable pageable){
		if (this.userService.currentUser().isAdmin()) {
			return ResponseEntity.ok(this.alarmTriggeringService.findAllForAdmin(pageable).map(AlarmTriggeringDTO::new));
		}
		return ResponseEntity.ok(this.alarmTriggeringService.findAllForDoctor(pageable).map(AlarmTriggeringDTO::new));
	}
	
}
