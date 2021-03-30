package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.AdminAlarmDTO;
import com.example.demo.dto.DoctorAlarmDTO;
import com.example.demo.mapper.AlarmMapper;
import com.example.demo.model.AdminAlarm;
import com.example.demo.model.DoctorAlarm;
import com.example.demo.service.AdminAlarmService;
import com.example.demo.service.DoctorAlarmService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Constants;

@RestController
@RequestMapping(value = "/api/alarms", produces = MediaType.APPLICATION_JSON_VALUE)
public class AlarmController {

	@Autowired
	private UserService userService;

	@Autowired
	private AdminAlarmService adminAlarmService;

	@Autowired
	private DoctorAlarmService doctorAlarmService;
		
	@Autowired
	private AlarmMapper alarmMapper;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")	
	public ResponseEntity<List<AdminAlarmDTO>> findAll(Pageable pageable, HttpServletResponse response){
		Page<AdminAlarm> alarms = this.adminAlarmService.findAll(pageable);
		response.setHeader(Constants.ENABLE_HEADER, Constants.FIRST_PAGE + ", " + Constants.LAST_PAGE);
		response.setHeader(Constants.FIRST_PAGE, alarms.isFirst() + "");
		response.setHeader(Constants.LAST_PAGE, alarms.isLast() + "");
		return ResponseEntity.ok(this.alarmMapper.adminMap(alarms.toList()));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")	
	public ResponseEntity<AdminAlarmDTO> create(@Valid @RequestBody AdminAlarmDTO alarmDTO){
		this.adminAlarmService.save(this.alarmMapper.map(alarmDTO));
		return ResponseEntity.ok(alarmDTO);
	}

	@GetMapping(value = "/{patientId}")
	@PreAuthorize("hasAuthority('DOCTOR')")	
	public ResponseEntity<List<DoctorAlarmDTO>> findAll(@PathVariable long patientId, Pageable pageable, HttpServletResponse response){
		Page<DoctorAlarm> alarms = this.doctorAlarmService.findAll(patientId, pageable);
		response.setHeader(Constants.ENABLE_HEADER, Constants.FIRST_PAGE + ", " + Constants.LAST_PAGE);
		response.setHeader(Constants.FIRST_PAGE, alarms.isFirst() + "");
		response.setHeader(Constants.LAST_PAGE, alarms.isLast() + "");
		return ResponseEntity.ok(this.alarmMapper.doctorMap(alarms.toList()));
	}

	@PostMapping(value = "/{patientId}")
	@PreAuthorize("hasAuthority('DOCTOR')")	
	public ResponseEntity<DoctorAlarmDTO> create(@PathVariable long patientId, @Valid @RequestBody DoctorAlarmDTO alarmDTO){
		this.doctorAlarmService.save(this.alarmMapper.map(patientId, alarmDTO));
		return ResponseEntity.ok(alarmDTO);
	}

	@PreAuthorize("hasAnyAuthority('ADMIN','DOCTOR')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		if (this.userService.currentUser().isAdmin()) {
			this.adminAlarmService.delete(id);
		}
		else {
			this.doctorAlarmService.delete(id);
		}
		return ResponseEntity.noContent().build();
	}
	
}
