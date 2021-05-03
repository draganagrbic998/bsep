package com.example.demo.service;

import com.example.demo.model.CertificateInfo;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.repository.CertificateInfoRepository;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CertificateInfoService {

	private final CertificateInfoRepository certificateInfoRepository;
	private final EmailService emailService;
	private final Logger logger;

	@Transactional(readOnly = true)
	public Page<CertificateInfo> findAll(Pageable pageable) {
		try {
			Page<CertificateInfo> response = this.certificateInfoRepository.findAll(pageable);
			this.logger.write(LogStatus.SUCCESS, String.format("Certificates page number %d successfully fetched.", pageable.getPageNumber()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching certificates page number %d.", pageable.getPageNumber()));
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public CertificateInfo findByAlias(String alias) {
		try {
			CertificateInfo response = this.certificateInfoRepository.findByAlias(alias);
			this.logger.write(LogStatus.SUCCESS, String.format("Certificate with alias %s successfully fetched.", alias));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching certificate with alias %s.", alias));
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public CertificateInfo save(CertificateInfo certificate) {
		try {
			CertificateInfo response = this.certificateInfoRepository.save(certificate);
			this.logger.write(LogStatus.SUCCESS, String.format("Certificate with id %d successfully saved.", response.getId()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while saving certificate.");
			throw e;
		}
	}
	
	@Transactional(readOnly = false)
	public CertificateInfo revoke(long id, String reason) {
		try {
			CertificateInfo certificate = this.certificateInfoRepository.findById(id).get();
			certificate.setRevoked(true);
			certificate.setRevocationDate(new Date());
			certificate.setRevocationReason(reason);
			certificate = this.certificateInfoRepository.save(certificate);
			this.emailService.sendInfoMail(certificate.getEmail(), certificate.getIssuerAlias() + "_" + certificate.getAlias() + ".jks", 
					reason, "Certificate Revoked - Bezbednost", "certificate-revoked");
			this.logger.write(LogStatus.SUCCESS, String.format("Certificate with id %d successfully revoked.", id));
			return certificate;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while revoking certificate with id %d.", id));
			throw e;
		}
	}

}
