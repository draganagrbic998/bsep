package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.DoctorAlarm;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.repository.DoctorAlarmRepository;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorAlarmService {

	private final DoctorAlarmRepository alarmRepository;
	private final Logger logger;
	
	@Transactional(readOnly = true)
	public Page<DoctorAlarm> findAll(long patientId, Pageable pageable){
		try {
			Page<DoctorAlarm> response = this.alarmRepository.findByPatientId(patientId, pageable);
			this.logger.write(LogStatus.SUCCESS, String.format("Doctor alarms page number %d successfully fetched.", pageable.getPageNumber()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching doctor alarms page number %d.", pageable.getPageNumber()));
			throw e;
		}
	}
	
	@Transactional(readOnly = false)
	public DoctorAlarm save(DoctorAlarm alarm) {
		try {
			DoctorAlarm response = this.alarmRepository.save(alarm);
			this.logger.write(LogStatus.SUCCESS, String.format("Doctor alarm with id %d successfully saved.", response.getId()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while saving doctor alarm.");
			throw e;
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(long id) {
		try {
			this.alarmRepository.deleteById(id);
			this.logger.write(LogStatus.SUCCESS, String.format("Doctor alarm with id %d successfully deleted.", id));
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while deleting doctor alarm with id %d.", id));
			throw e;
		}
	}
	
	@Transactional(readOnly = true)
	public List<DoctorAlarm> findAll(long patientId){
		return this.alarmRepository.findByPatientId(patientId);
	}

}
