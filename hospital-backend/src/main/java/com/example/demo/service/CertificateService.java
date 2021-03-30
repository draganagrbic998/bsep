package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CertificateRequestDTO;

@Service
public class CertificateService {

	@Autowired
	private RestTemplate restTemplate;

	public void sendCertificateRequest(CertificateRequestDTO certificateRequestDTO) {
		this.restTemplate.postForEntity("https://localhost:8080/api/certificates/requests/create",
				certificateRequestDTO, CertificateRequestDTO.class).getBody();
	}

}
