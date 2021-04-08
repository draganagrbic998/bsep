package com.example.demo.controller; 

import javax.validation.Valid;

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
import com.example.demo.service.AdminAlarmService;
import com.example.demo.service.DoctorAlarmService;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/alarms", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AlarmController {
	
	private final UserService userService;
	private final AdminAlarmService adminAlarmService;
	private final DoctorAlarmService doctorAlarmService;
	private final AlarmMapper alarmMapper;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ADMIN')")	
	public ResponseEntity<Page<AdminAlarmDTO>> findAll(Pageable pageable){
		return ResponseEntity.ok(this.adminAlarmService.findAll(pageable).map(AdminAlarmDTO::new));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")	
	public ResponseEntity<AdminAlarmDTO> create(@Valid @RequestBody AdminAlarmDTO alarmDTO){
		this.adminAlarmService.save(this.alarmMapper.map(alarmDTO));
		return ResponseEntity.ok(alarmDTO);
	}

	@GetMapping(value = "/{patientId}")
	@PreAuthorize("hasAuthority('DOCTOR')")	
	public ResponseEntity<Page<DoctorAlarmDTO>> findAll(@PathVariable long patientId, Pageable pageable){
		return ResponseEntity.ok(this.doctorAlarmService.findAll(patientId, pageable).map(DoctorAlarmDTO::new));
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
