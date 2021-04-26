package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.MaliciousIpAddress;
import com.example.demo.repository.MaliciousIpAddressRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MaliciousIpAddressService {

	private final MaliciousIpAddressRepository ipAddressRepository;
	
	@Transactional(readOnly = false)
	public MaliciousIpAddress save(MaliciousIpAddress ipAddress) {
		try {
			return this.ipAddressRepository.save(ipAddress);
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public boolean hasIpAddress(String ipAddress) {
		return this.ipAddressRepository.findByName(ipAddress) != null;
	}
	
}
