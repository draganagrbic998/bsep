package com.example.demo.service;

import com.example.demo.model.CertificateInfo;
import com.example.demo.repository.CertificateInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CertificateInfoService {

	private final CertificateInfoRepository certificateInfoRepository;

	@Autowired
	public CertificateInfoService(CertificateInfoRepository certificateInfoRepository) {
		this.certificateInfoRepository = certificateInfoRepository;
	}

	public Page<CertificateInfo> findAll(Pageable pageable) {
		return this.certificateInfoRepository.findAll(pageable);
	}

	public CertificateInfo findByAlias(String alias) {
		return this.certificateInfoRepository.findFirstByAliasContainingIgnoreCase(alias);
	}

	@Transactional(readOnly = false)
	public CertificateInfo save(CertificateInfo certInfo) {
		return this.certificateInfoRepository.save(certInfo);
	}
	
}
