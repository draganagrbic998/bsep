package com.example.demo.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.AlarmTriggering;
import com.example.demo.model.HasIpAddress;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.repository.AlarmTriggeringRepository;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AlarmTriggeringService {

	private final AlarmTriggeringRepository alarmTriggeringRepository;
	private final Logger logger;
	
	@Transactional(readOnly = true)
	public Page<AlarmTriggering> findAllForAdmin(Pageable pageable, boolean low, boolean moderate, boolean high, boolean extreme) {
		try {
			Page<AlarmTriggering> response = this.alarmTriggeringRepository.filterAdmin(pageable, low, moderate, high, extreme);
			this.logger.write(LogStatus.SUCCESS, String.format("Admin alarm triggerings page number %d successfully fetched.", pageable.getPageNumber()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching admin alarm triggerings page number %d.", pageable.getPageNumber()));
			throw e;
		}
	}
	
	@Transactional(readOnly = true)
	public Page<AlarmTriggering> findAllForDoctor(Pageable pageable, boolean low, boolean moderate, boolean high, boolean extreme) {
		try {
			Page<AlarmTriggering> response = this.alarmTriggeringRepository.filterDoctor(pageable, low, moderate, high, extreme);
			this.logger.write(LogStatus.SUCCESS, String.format("Doctor alarm triggerings page number %d successfully fetched.", pageable.getPageNumber()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching doctor alarm triggerings page number %d.", pageable.getPageNumber()));
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public AlarmTriggering save(AlarmTriggering alarmTriggering) {
		return this.alarmTriggeringRepository.save(alarmTriggering);
	}
	
	public long countSameIpAddress(List<HasIpAddress> list) {
		Set<String> ipAddresses = list.stream().map(HasIpAddress::ipAddress).collect(Collectors.toSet());
		long maxCount = -1;
		for (String ipAddress: ipAddresses) {
			long count = list.stream().filter(item -> item.ipAddress().equals(ipAddress)).count();
			if (count > maxCount) maxCount = count;
		}
		return maxCount;
	}

}
