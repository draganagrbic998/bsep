package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.AlarmTriggering;
import com.example.demo.model.AlarmType;

public interface AlarmTriggeringRepository extends JpaRepository<AlarmTriggering, Long> {

	public Page<AlarmTriggering> findByPatientIdNullOrderByDateDesc(Pageable pageable);
	public Page<AlarmTriggering> findByPatientIdNotNullOrderByDateDesc(Pageable pageable);
		
	@Query("select count(a) from AlarmTriggering a where "
			+ "a.type like :type and "
			+ "(cast(:start as date) is null or a.date >= :start) and "
			+ "(cast(:end as date) is null or a.date <= :end)")
	public long report(AlarmType type, Date start, Date end);

}
