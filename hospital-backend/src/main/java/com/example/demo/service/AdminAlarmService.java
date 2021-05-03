package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.AdminAlarm;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.repository.AdminAlarmRepository;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminAlarmService {

	private final AdminAlarmRepository alarmRepository;
	private final Logger logger;
		
	@Transactional(readOnly = true)
	public Page<AdminAlarm> findAll(Pageable pageable){
		try {
			Page<AdminAlarm> response = this.alarmRepository.findAll(pageable);
			this.logger.write(LogStatus.SUCCESS, String.format("Admin alarms page number %d successfully fetched.", pageable.getPageNumber()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching admin alarms page number %d.", pageable.getPageNumber()));
			throw e;
		}
	}
	
	@Transactional(readOnly = false)
	public AdminAlarm save(AdminAlarm alarm) {
		try {
			AdminAlarm response = this.alarmRepository.save(alarm);
			this.logger.write(LogStatus.SUCCESS, String.format("Admin alarm with id %d successfully saved.", response.getId()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while saving admin alarm.");
			throw e;
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(long id) {
		try {
			this.alarmRepository.deleteById(id);
			this.logger.write(LogStatus.SUCCESS, String.format("Admin alarm with id %d successfully deleted.", id));
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while deleting admin alarm with id %d.", id));
			throw e;
		}
	}
	
	@Transactional(readOnly = true)
	public List<AdminAlarm> findAll(){
		return this.alarmRepository.findAll();
	}

}
