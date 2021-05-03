package com.example.demo.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ReportDTO;
import com.example.demo.model.enums.AlarmType;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.repository.AlarmTriggeringRepository;
import com.example.demo.repository.LogRepository;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportService {

	private final LogRepository logRepository;
	private final AlarmTriggeringRepository alarmRepository;
	private final Logger logger;
		
	@Transactional(readOnly = true)
	public ReportDTO report(Date start, Date end) {
		try {
			ReportDTO response = new ReportDTO(
				this.logRepository.report(LogStatus.SUCCESS, start, end),
				this.logRepository.report(LogStatus.INFO, start, end),
				this.logRepository.report(LogStatus.WARNING, start, end),
				this.logRepository.report(LogStatus.ERROR, start, end),
				this.logRepository.report(LogStatus.FATAL, start, end),
				this.alarmRepository.report(AlarmType.PATIENT, start, end),
				this.alarmRepository.report(AlarmType.LOG, start, end),
				this.alarmRepository.report(AlarmType.DOS, start, end),
				this.alarmRepository.report(AlarmType.BRUTE_FORCE, start, end)
			);
			this.logger.write(LogStatus.SUCCESS, "Report successfully fetched.");
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while fetching report.");
			throw e;
		}
	}
	
}
