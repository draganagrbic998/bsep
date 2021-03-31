package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.AlarmTriggering;

public interface AlarmTriggeringRepository extends JpaRepository<AlarmTriggering, Long> {

	public Page<AlarmTriggering> findByPatientIdNullOrderByDateDesc(Pageable pageable);
	public Page<AlarmTriggering> findByPatientIdNotNullOrderByDateDesc(Pageable pageable);
	
	@Query("select count(m) from AlarmTriggering m where "
			+ "(cast(:start as date) is null or m.date >= :start) and "
			+ "(cast(:end as date) is null or m.date <= :end)")
	public long report(Date start, Date end);
	
}
