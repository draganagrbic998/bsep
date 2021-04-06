package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ReportDTO;
import com.example.demo.dto.ReportSearchDTO;
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
			this.logRepository.report(LogStatus.SUCCESS.name(), searchDTO.getStart(), searchDTO.getEnd()),
			this.logRepository.report(LogStatus.INFO.name(), searchDTO.getStart(), searchDTO.getEnd()),
			this.logRepository.report(LogStatus.WARNING.name(), searchDTO.getStart(), searchDTO.getEnd()),
			this.logRepository.report(LogStatus.ERROR.name(), searchDTO.getStart(), searchDTO.getEnd()),
			this.logRepository.report(LogStatus.FATAL.name(), searchDTO.getStart(), searchDTO.getEnd()),
			this.alarmRepository.report(searchDTO.getStart(), searchDTO.getEnd())
		);
	}
	
}
