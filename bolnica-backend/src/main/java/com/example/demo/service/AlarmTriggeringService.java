package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SearchDTO;
import com.example.demo.model.AlarmTriggering;
import com.example.demo.repository.AlarmTriggeringRepository;import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AlarmTriggeringService {

	@Autowired
	private AlarmTriggeringRepository alarmTriggeringRepository;
	
	public Page<AlarmTriggering> findAll(Pageable pageable, SearchDTO searchDTO) {
		return this.alarmTriggeringRepository.findAll(pageable, 
			searchDTO.getInsuredNumber(), 
			searchDTO.getFirstName(), 
			searchDTO.getLastName(), 
			searchDTO.getDate());
	}
	
	@Transactional(readOnly = false)
	public AlarmTriggering save(AlarmTriggering alarmTriggering) {
		return this.alarmTriggeringRepository.save(alarmTriggering);
	}
	
}
