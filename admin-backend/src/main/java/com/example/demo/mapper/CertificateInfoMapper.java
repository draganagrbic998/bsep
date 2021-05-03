package com.example.demo.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.certificate.CertificateInfoDTO;
import com.example.demo.model.CertificateInfo;
import com.example.demo.repository.CertificateInfoRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CertificateInfoMapper {

	private final CertificateInfoRepository certificateRepository;
	
	@Transactional(readOnly = true)
	public CertificateInfoDTO map(CertificateInfo certificate, int mappingLevel) {
		CertificateInfoDTO certificateDTO = new CertificateInfoDTO(certificate);
		List<CertificateInfo> certificates = this.certificateRepository.findIssued(certificate.getId());
		certificateDTO.setNumIssued(certificates.size());
        if (mappingLevel > 0)
        	certificateDTO.setIssued(certificates.stream().map(ci -> this.map(ci, mappingLevel - 1)).collect(Collectors.toList()));
		return certificateDTO;
	}
	
}
