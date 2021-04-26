package com.example.demo.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Log;
import com.example.demo.model.LogStatus;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {

	@Query(nativeQuery = true, value = "select * from Log m where "
			+ "(lower(m.mode) like lower(concat('%', :mode, '%')) or m.mode ~ :mode) and "
			+ "(lower(m.status) like lower(concat('%', :status, '%')) or m.status ~ :status) and "
			+ "(lower(m.ip_address) like lower(concat('%', :ipAddress, '%')) or m.ip_address ~ :ipAddress) and "
			+ "(lower(m.description) like lower(concat('%', :description, '%')) or m.description ~ m.description) and "
			+ "(:date = 'empty' or date(m.date) = to_date(:date, 'yyyy-mm-dd')) "
			+ "order by m.date desc")
	public Page<Log> findAll(Pageable pageable, String mode, String status, String ipAddress, String description, String date);
	
	@Query("select count(l) from Log l where "
			+ "l.status like :status and "
			+ "(cast(:start as date) is null or l.date >= :start) and "
			+ "(cast(:end as date) is null or l.date <= :end)")
	public long report(LogStatus status, Date start, Date end);
	
}
