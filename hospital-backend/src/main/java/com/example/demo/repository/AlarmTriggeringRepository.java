package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.AlarmTriggering;
import com.example.demo.model.enums.AlarmType;

public interface AlarmTriggeringRepository extends JpaRepository<AlarmTriggering, Long> {

	@Query("select a from AlarmTriggering a where a.patient.id is null and "
			+ "((a.risk like 'LOW' and :low = true) or "
			+ "(a.risk like 'MODERATE' and :moderate = true) or "
			+ "(a.risk like 'HIGH' and :high = true) or "
			+ "(a.risk like 'EXTREME' and :extreme = true)) "
			+ "order by a.date desc")
	public Page<AlarmTriggering> filterAdmin(Pageable pageable, boolean low, boolean moderate, boolean high, boolean extreme);

	@Query("select a from AlarmTriggering a where a.patient.id is not null and "
			+ "((a.risk like 'LOW' and :low = true) or "
			+ "(a.risk like 'MODERATE' and :moderate = true) or "
			+ "(a.risk like 'HIGH' and :high = true) or "
			+ "(a.risk like 'EXTREME' and :extreme = true)) "
			+ "order by a.date desc")
	public Page<AlarmTriggering> filterDoctor(Pageable pageable, boolean low, boolean moderate, boolean high, boolean extreme);

	@Query("select count(a) from AlarmTriggering a where "
			+ "a.type like :type and "
			+ "(cast(:start as date) is null or a.date >= :start) and "
			+ "(cast(:end as date) is null or a.date <= :end)")
	public long report(AlarmType type, Date start, Date end);

}
