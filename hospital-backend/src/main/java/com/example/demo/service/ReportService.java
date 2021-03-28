package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ReportDTO;
import com.example.demo.model.LogStatus;
import com.example.demo.repository.AdminAlarmRepository;
import com.example.demo.repository.LogRepository;

@Service
@Transactional(readOnly = true)
public class ReportService {

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private AdminAlarmRepository alarmRepository;
		
	public ReportDTO report() {
		return new ReportDTO(
			this.logRepository.countByStatus(LogStatus.INFO.name()),
			this.logRepository.countByStatus(LogStatus.WARNING.name()),
			this.logRepository.countByStatus(LogStatus.ERROR.name()),
			this.logRepository.countByStatus(LogStatus.FATAL.name()),
			this.alarmRepository.countByStatusAndParam(true, LogStatus.INFO.name()),
			this.alarmRepository.countByStatusAndParam(true, LogStatus.WARNING.name()),
			this.alarmRepository.countByStatusAndParam(true, LogStatus.ERROR.name()),
			this.alarmRepository.countByStatusAndParam(true, LogStatus.FATAL.name()),
			this.alarmRepository.countByStatus(false)
		);
	}
	
}
