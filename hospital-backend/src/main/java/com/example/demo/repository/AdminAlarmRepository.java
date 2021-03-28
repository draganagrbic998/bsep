package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.AdminAlarm;

@Repository
public interface AdminAlarmRepository extends JpaRepository<AdminAlarm, Long> {

	public long countByStatus(boolean status);
	public long countByStatusAndParam(boolean status, String param);
	
}
