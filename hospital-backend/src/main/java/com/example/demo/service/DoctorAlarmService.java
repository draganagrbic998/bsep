package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Alarm;
import com.example.demo.repository.AlarmRepository;

@Service
@Transactional(readOnly = true)
public class AlarmService {

	@Autowired
	private AlarmRepository alarmRepository;
	
	public Page<Alarm> findAll(long patientId, Pageable pageable){
		return this.alarmRepository.findByPatientId(patientId, pageable);
	}
	
	@Transactional(readOnly = false)
	public Alarm save(Alarm alarm) {
		return this.alarmRepository.save(alarm);
	}
	
	@Transactional(readOnly = false)
	public void delete(long id) {
		this.alarmRepository.deleteById(id);
	}
	
}
