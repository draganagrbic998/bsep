package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	@Query("select p from Patient p where "
			+ "lower(p.firstName) like lower(concat('%', :search, '%')) or "
			+ "lower(p.lastName) like lower(concat('%', :search, '%')) or "
			+ "lower(p.address) like lower(concat('%', :search, '%')) or "
			+ "lower(p.city) like lower(concat('%', :search, '%')) "
			+ "order by p.lastName, p.firstName")
	public Page<Patient> findAll(Pageable pageable, String search);
		
}
