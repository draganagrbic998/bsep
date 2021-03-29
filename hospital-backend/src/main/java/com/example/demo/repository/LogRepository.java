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
			+ "lower(m.status) like lower(concat('%', :status, '%')) and "
			+ "lower(m.description) like lower(concat('%', :description, '%')) and "
			+ "lower(m.userName) like lower(concat('%', :userName, '%')) and "
			+ "lower(m.computerName) like lower(concat('%', :computerName, '%')) and "
			+ "lower(m.serviceName) like lower(concat('%', :serviceName, '%')) "
			+ "order by m.date desc")
	public Page<Log> findAll(Pageable pageable, String status, String description, String userName, String computerName, String serviceName);
	
	@Query("select count(m) from Log m where "
			+ "lower(m.status) like lower(:status) and "
			+ "(cast(:start as date) is null or m.date >= :start) and "
			+ "(cast(:end as date) is null or m.date <= :end)")
	public long report(String status, Date start, Date end);
	
}
