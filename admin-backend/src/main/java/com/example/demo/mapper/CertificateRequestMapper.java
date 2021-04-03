package com.example.demo.mapper;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.model.CertificateRequest;
import org.springframework.stereotype.Component;

@Component
public class CertificateRequestMapper {

	public CertificateRequest map(CertificateRequestDTO certificateRequestDTO) {
		return new CertificateRequest(certificateRequestDTO);
	}

	public CertificateRequestDTO map(CertificateRequest certificateRequest) {
		return new CertificateRequestDTO(certificateRequest);
	}
}
