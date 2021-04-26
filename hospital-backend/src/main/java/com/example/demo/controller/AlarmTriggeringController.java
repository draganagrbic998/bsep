package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AlarmTriggeringDTO;
import com.example.demo.mapper.AlarmTriggeringMapper;
import com.example.demo.service.AlarmTriggeringService;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/alarm-triggerings", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAnyAuthority('ADMIN','DOCTOR')")
@AllArgsConstructor
public class AlarmTriggeringController {

	private final UserService userService;
	private final AlarmTriggeringService alarmTriggeringService;
	private final AlarmTriggeringMapper alarmTriggeringMapper;

	@GetMapping
	public ResponseEntity<Page<AlarmTriggeringDTO>> findAll(Pageable pageable, @RequestParam boolean low,
			@RequestParam boolean moderate, @RequestParam boolean high, @RequestParam boolean extreme) {
		if (this.userService.currentUser().isAdmin()) {
			return ResponseEntity
					.ok(this.alarmTriggeringService.findAllForAdmin(pageable, low, moderate, high, extreme).map(AlarmTriggeringDTO::new));
		}
		return ResponseEntity
				.ok(this.alarmTriggeringMapper.map(this.alarmTriggeringService.findAllForDoctor(pageable, low, moderate, high, extreme)));
	}

}
