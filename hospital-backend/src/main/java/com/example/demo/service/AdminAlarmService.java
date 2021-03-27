package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.AdminAlarm;
import com.example.demo.repository.AdminAlarmRepository;

@Service
@Transactional(readOnly = true)
public class AdminAlarmService {

	@Autowired
	private AdminAlarmRepository alarmRepository;
	
	public Page<AdminAlarm> findAll(Pageable pageable){
		return this.alarmRepository.findAll(pageable);
	}
	
	public List<AdminAlarm> findAll(){
		return this.alarmRepository.findAll();
	}

	@Transactional(readOnly = false)
	public AdminAlarm save(AdminAlarm alarm) {
		return this.alarmRepository.save(alarm);
	}
	
	@Transactional(readOnly = false)
	public void delete(long id) {
		this.alarmRepository.deleteById(id);
	}
	
}
