package com.example.demo.mapper;

import com.example.demo.dto.certificate.CertificateRequestDTO;
import com.example.demo.model.CertificateRequest;
import com.example.demo.model.enums.CertificateType;

import org.springframework.stereotype.Component;

@Component
public class CertificateRequestMapper {

	public CertificateRequest map(CertificateRequestDTO requestDTO) {
		CertificateRequest request = new CertificateRequest();
		request.setAlias(requestDTO.getAlias());
		request.setCommonName(requestDTO.getCommonName());
		request.setOrganization(requestDTO.getOrganization());
		request.setOrganizationUnit(requestDTO.getOrganizationUnit());
		request.setCountry(requestDTO.getCountry());
		request.setEmail(requestDTO.getEmail());
		request.setTemplate(requestDTO.getTemplate());
		request.setType(CertificateType.valueOf(requestDTO.getType()));
		request.setPath(requestDTO.getPath());
		request.setDomain(requestDTO.getDomain());
		return request;
	}

}
