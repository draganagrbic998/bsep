package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.demo.model.CertificateRequest;
import com.example.demo.repository.CertificateRequestRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CertificateRequestService {

	private final CertificateRequestRepository certificateRequestRepository;

	public CertificateRequest save(CertificateRequest certificateRequest) {
		return this.certificateRequestRepository.save(certificateRequest);
	}

	public Page<CertificateRequest> findAll(Pageable pageable) {
		return this.certificateRequestRepository.findAll(pageable);
	}

	public CertificateRequest findOne(long id) {
		return this.certificateRequestRepository.findById(id).get();
	}
	
	public void delete(long id) {
		this.certificateRequestRepository.deleteById(id);
	}
	
}
