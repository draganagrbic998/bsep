package com.example.demo.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.model.AlarmTriggering;
import com.example.demo.model.HasIpAddress;
import com.example.demo.repository.AlarmTriggeringRepository;

import lombok.AllArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AlarmTriggeringService {

	private final AlarmTriggeringRepository alarmTriggeringRepository;
	
	public Page<AlarmTriggering> findAllForAdmin(Pageable pageable) {
		return this.alarmTriggeringRepository.findByPatientIdNullOrderByDateDesc(pageable);
	}
	
	public Page<AlarmTriggering> findAllForDoctor(Pageable pageable) {
		return this.alarmTriggeringRepository.findByPatientIdNotNullOrderByDateDesc(pageable);
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
			if (count > maxCount) {
				maxCount = count;
			}
		}
		return maxCount;
	}

}
