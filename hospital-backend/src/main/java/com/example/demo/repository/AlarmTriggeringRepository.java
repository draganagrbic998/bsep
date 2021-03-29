package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.AlarmTriggering;

public interface AlarmTriggeringRepository extends JpaRepository<AlarmTriggering, Long> {

	public Page<AlarmTriggering> findByPatientIdNull(Pageable pageable);
	public Page<AlarmTriggering> findByPatientIdNotNull(Pageable pageable);
	
	
}
