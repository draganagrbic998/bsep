package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Alarm;
import com.example.demo.repository.AlarmRepository;

@Service
@Transactional(readOnly = true)
public class AlarmService {

	@Autowired
	private AlarmRepository alarmRepository;
	
	@Transactional(readOnly = false)
	public Alarm save(Alarm alarm) {
		return this.alarmRepository.save(alarm);
	}
	
}
