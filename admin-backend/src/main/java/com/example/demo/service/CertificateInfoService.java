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

	private final CertificateInfoRepository certificateRepository;
	private final EmailService emailService;

	public Page<CertificateInfo> findAll(Pageable pageable) {
		return this.certificateRepository.findAll(pageable);
	}

	public CertificateInfo findByAlias(String alias) {
		return this.certificateRepository.findByAlias(alias);
	}

	@Transactional(readOnly = false)
	public CertificateInfo save(CertificateInfo certificate) {
		return this.certificateRepository.save(certificate);
	}
	
	@Transactional(readOnly = false)
	public void revoke(long id, String reason) {
		CertificateInfo certificate = this.certificateRepository.findById(id).get();
		certificate.setRevoked(true);
		certificate.setRevocationDate(new Date());
		certificate.setRevocationReason(reason);
		this.certificateRepository.save(certificate);
		String certFileName = certificate.getIssuerAlias() + "_" + certificate.getAlias() + "_" + certificate.getOrganizationUnit() + ".jks";
		this.emailService.sendInfoMail(certificate.getEmail(), certFileName, reason, "Certificate Revoked - Bezbednost", Constants.REVOKED_TEMPLATE);
	}

}
