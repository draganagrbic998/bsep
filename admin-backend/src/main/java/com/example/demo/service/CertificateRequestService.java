package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.CertificateRequest;
import com.example.demo.repository.CertificateRequestRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
@Transactional(readOnly = true)
public class CertificateRequestService {

	private final CertificateRequestRepository requestRepository;

	public Page<CertificateRequest> findAll(Pageable pageable) {
		return this.requestRepository.findAll(pageable);
	}

	public CertificateRequest findOne(long id) {
		return this.requestRepository.findById(id).get();
	}
	
	@Transactional(readOnly = false)
	public CertificateRequest save(CertificateRequest request) {
		return this.requestRepository.save(request);
	}

	@Transactional(readOnly = false)
	public void delete(long id) {
		this.requestRepository.deleteById(id);
	}
	
}
