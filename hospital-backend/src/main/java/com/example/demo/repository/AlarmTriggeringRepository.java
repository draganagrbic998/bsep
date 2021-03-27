package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.AlarmTriggering;

public interface AlarmTriggeringRepository extends JpaRepository<AlarmTriggering, Long> {

	@Query("select a from AlarmTriggering a where "
			+ "lower(a.patient.insuredNumber) like lower(concat('%', :insuredNumber, '%')) and "
			+ "lower(a.patient.firstName) like lower(concat('%', :firstName, '%')) and "
			+ "lower(a.patient.lastName) like lower(concat('%', :lastName, '%')) and "
			+ "(cast(:date as date) is null or a.date <= :date) "
			+ "order by a.date desc")
	public Page<AlarmTriggering> findAll(Pageable pageable, String insuredNumber, String firstName, String lastName, Date date);

}
