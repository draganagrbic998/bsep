package com.example.demo.service;

import com.example.demo.model.CertificateInfo;
import com.example.demo.repository.CertificateInfoRepository;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CertificateInfoService {

	private final CertificateInfoRepository certificateInfoRepository;
	private final EmailService emailService;

	public Page<CertificateInfo> findAll(Pageable pageable) {
		return this.certificateInfoRepository.findAll(pageable);
	}

	public CertificateInfo findByAlias(String alias) {
		return this.certificateInfoRepository.findByAlias(alias);
	}

	@Transactional(readOnly = false)
	public CertificateInfo save(CertificateInfo certificate) {
		return this.certificateInfoRepository.save(certificate);
	}
	
	@Transactional(readOnly = false)
	public CertificateInfo revoke(long id, String reason) {
		CertificateInfo certificate = this.certificateInfoRepository.findById(id).get();
		certificate.setRevoked(true);
		certificate.setRevocationDate(new Date());
		certificate.setRevocationReason(reason);
		certificate = this.certificateInfoRepository.save(certificate);
		String fileName = certificate.getIssuerAlias() + "_" + certificate.getAlias() + "_" + certificate.getOrganizationUnit() + ".jks";
		this.emailService.sendInfoMail(certificate.getEmail(), fileName, reason, "Certificate Revoked - Bezbednost", Constants.REVOKED_TEMPLATE);
		return certificate;
	}

}
