package com.example.demo.repository;

import com.example.demo.model.MaliciousIpAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaliciousIpAddressRepository extends JpaRepository<MaliciousIpAddress, Long> {

	public MaliciousIpAddress findByName(String name);
	
}
