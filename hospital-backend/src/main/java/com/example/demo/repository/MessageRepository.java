package com.example.demo.repository;

import com.example.demo.model.Message;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query("select m from Message m where "
			+ "lower(m.patient.insuredNumber) like lower(concat('%', :insuredNumber, '%')) and "
			+ "lower(m.patient.firstName) like lower(concat('%', :firstName, '%')) and "
			+ "lower(m.patient.lastName) like lower(concat('%', :lastName, '%')) and "
			+ "(cast(:date as date) is null or cast(m.date as date) = :date) "
			+ "order by m.date desc")
	public Page<Message> findAll(Pageable pageable, String insuredNumber, String firstName, String lastName, Date date);

}
