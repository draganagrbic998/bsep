package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ReportDTO;
import com.example.demo.dto.ReportSearchDTO;
import com.example.demo.model.AlarmType;
import com.example.demo.model.LogStatus;
import com.example.demo.repository.AlarmTriggeringRepository;
import com.example.demo.repository.LogRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ReportService {

	private final LogRepository logRepository;
	private final AlarmTriggeringRepository alarmRepository;
		
	public ReportDTO report(ReportSearchDTO searchDTO) {
		return new ReportDTO(
			this.logRepository.report(LogStatus.SUCCESS, searchDTO.getStart(), searchDTO.getEnd()),
			this.logRepository.report(LogStatus.INFO, searchDTO.getStart(), searchDTO.getEnd()),
			this.logRepository.report(LogStatus.WARNING, searchDTO.getStart(), searchDTO.getEnd()),
			this.logRepository.report(LogStatus.ERROR, searchDTO.getStart(), searchDTO.getEnd()),
			this.logRepository.report(LogStatus.FATAL, searchDTO.getStart(), searchDTO.getEnd()),
			this.alarmRepository.report(AlarmType.PATIENT, searchDTO.getStart(), searchDTO.getEnd()),
			this.alarmRepository.report(AlarmType.LOG, searchDTO.getStart(), searchDTO.getEnd()),
			this.alarmRepository.report(AlarmType.DOS, searchDTO.getStart(), searchDTO.getEnd()),
			this.alarmRepository.report(AlarmType.BRUTE_FORCE, searchDTO.getStart(), searchDTO.getEnd())
		);
	}
	
}
