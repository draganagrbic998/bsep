package com.example.demo.mapper;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.model.CertificateRequest;
import com.example.demo.model.Template;

import org.springframework.stereotype.Component;

@Component
public class CertificateRequestMapper {

	public CertificateRequest map(CertificateRequestDTO requestDTO) {
		CertificateRequest request = new CertificateRequest();
		request.setAlias(requestDTO.getAlias());
		request.setOrganization(requestDTO.getOrganization());
		request.setOrganizationUnit(requestDTO.getOrganizationUnit());
		request.setCommonName(requestDTO.getCommonName());
		request.setCountry(requestDTO.getCountry());
		request.setEmail(requestDTO.getEmail());
		request.setTemplate(Template.valueOf(requestDTO.getTemplate()));
		request.setPath(requestDTO.getPath());
		return request;
	}

}
