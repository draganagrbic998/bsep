package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.CertificateRequest;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.repository.CertificateRequestRepository;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CertificateRequestService {

	private final CertificateRequestRepository certificateRequestRepository;
	private final Logger logger;

	@Transactional(readOnly = true)
	public Page<CertificateRequest> findAll(Pageable pageable) {
		try {
			Page<CertificateRequest> response = this.certificateRequestRepository.findAll(pageable);
			this.logger.write(LogStatus.SUCCESS, String.format("Certificate requests page number %d successfully fetched.", pageable.getPageNumber()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching certificate requests page number %d.", pageable.getPageNumber()));
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public CertificateRequest findOne(long id) {
		try {
			CertificateRequest response = this.certificateRequestRepository.findById(id).get();
			this.logger.write(LogStatus.SUCCESS, String.format("Certificate request with id %d successfully fetched.", id));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occurred while fetching certificate request with id %d.", id));
			throw e;
		}
	}
	
	@Transactional(readOnly = false)
	public CertificateRequest save(CertificateRequest request) {
		try {
			CertificateRequest response = this.certificateRequestRepository.save(request);
			this.logger.write(LogStatus.SUCCESS, String.format("Certificate request with id %d successfully saved.", response.getId()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while saving certificate request.");
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public void delete(long id) {
		try {
			this.certificateRequestRepository.deleteById(id);
			this.logger.write(LogStatus.SUCCESS, String.format("Certificate request with id %d successfully deleted.", id));
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while deleting certificate request with id %d.", id));
			throw e;
		}
	}
	
}
