package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.DoctorAlarm;
import com.example.demo.repository.DoctorAlarmRepository;

@Service
@Transactional(readOnly = true)
public class DoctorAlarmService {

	@Autowired
	private DoctorAlarmRepository alarmRepository;
	
	public Page<DoctorAlarm> findAll(long patientId, Pageable pageable){
		return this.alarmRepository.findByPatientId(patientId, pageable);
	}
	
	public List<DoctorAlarm> findAll(long patientId){
		return this.alarmRepository.findByPatientId(patientId);
	}

	@Transactional(readOnly = false)
	public DoctorAlarm save(DoctorAlarm alarm) {
		return this.alarmRepository.save(alarm);
	}
	
	@Transactional(readOnly = false)
	public void delete(long id) {
		this.alarmRepository.deleteById(id);
	}
	
}