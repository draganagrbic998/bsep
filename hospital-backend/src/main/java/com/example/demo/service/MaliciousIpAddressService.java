package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.MaliciousIpAddress;
import com.example.demo.repository.MaliciousIpAddressRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MaliciousIpAddressService {

	private final MaliciousIpAddressRepository ipAddressRepository;
	
	@Transactional(readOnly = false)
	public MaliciousIpAddress save(MaliciousIpAddress ipAddress) {
		return this.hasIpAddress(ipAddress.getName()) ? null : this.ipAddressRepository.save(ipAddress);
	}
	
	@Transactional(readOnly = true)
	public boolean hasIpAddress(String ipAddress) {
		return this.ipAddressRepository.findByName(ipAddress) != null;
	}
	
}
