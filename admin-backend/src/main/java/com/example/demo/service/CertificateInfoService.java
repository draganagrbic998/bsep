package com.example.demo.service;

import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.model.CertificateInfo;
import com.example.demo.repository.CertificateInfoRepository;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

import java.util.Date;

import javax.mail.MessagingException;

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
		return this.certificateInfoRepository.findByAliasIgnoreCase(alias);
	}

	@Transactional(readOnly = false)
	public CertificateInfo save(CertificateInfo certInfo) {
		return this.certificateInfoRepository.save(certInfo);
	}
	
	@Transactional(readOnly = false)
	public void revoke(long id, String revokeReason) throws MessagingException {
		CertificateInfo certificate = this.certificateInfoRepository.findById(id).orElse(null);
		if (certificate == null) {
			throw new CertificateNotFoundException(String.valueOf(id));
		}
		certificate.setRevoked(true);
		certificate.setRevocationDate(new Date());
		certificate.setRevocationReason(revokeReason);
		this.certificateInfoRepository.save(certificate);
		String certFileName = certificate.getIssuerAlias() + "_" + certificate.getAlias() + "_" + certificate.getOrganizationUnit() + ".jks";
		this.emailService.sendInfoMail(certificate.getEmail(), certFileName, revokeReason, 
				"Certificate Revoked - Bezbednost", Constants.REVOKED_TEMPLATE);
	}

}
