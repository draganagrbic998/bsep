package com.example.demo.repository;

import com.example.demo.model.DoctorAlarm;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorAlarmRepository extends JpaRepository<DoctorAlarm, Long> {

	public List<DoctorAlarm> findByPatientId(long patientId);
	public Page<DoctorAlarm> findByPatientId(long patientId, Pageable pageable);

}
