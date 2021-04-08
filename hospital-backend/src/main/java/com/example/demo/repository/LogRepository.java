package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

	@Query("select m from Log m where "
			+ "lower(m.mode) like lower(concat('%', :mode, '%')) and "
			+ "lower(m.status) like lower(concat('%', :status, '%')) and "
			+ "lower(m.ipAddress) like lower(concat('%', :ipAddress, '%')) and "
			+ "lower(m.description) like lower(concat('%', :description, '%')) and "
			+ "(cast(:date as date) is null or cast(m.date as date) = :date) "
			+ "order by m.date desc")
	public Page<Log> findAll(Pageable pageable, String mode, String status, String ipAddress, String description, Date date);
	
	@Query("select count(m) from Log m where "
			+ "lower(m.status) like lower(concat('%', :status, '%')) and "
			+ "(cast(:start as date) is null or m.date >= :start) and "
			+ "(cast(:end as date) is null or m.date <= :end)")
	public long report(String status, Date start, Date end);
	
}
