package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.AlarmTriggering;
import com.example.demo.repository.AlarmTriggeringRepository;

import lombok.AllArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AlarmTriggeringService {

	private final AlarmTriggeringRepository alarmTriggeringRepository;
	
	public Page<AlarmTriggering> findAllForAdmin(Pageable pageable) {
		return this.alarmTriggeringRepository.findByPatientIdNullOrderByDateDesc(pageable);
	}
	
	public Page<AlarmTriggering> findAllForDoctor(Pageable pageable) {
		return this.alarmTriggeringRepository.findByPatientIdNotNullOrderByDateDesc(pageable);
	}

	@Transactional(readOnly = false)
	public AlarmTriggering save(AlarmTriggering alarmTriggering) {
		return this.alarmTriggeringRepository.save(alarmTriggering);
	}
	
}
