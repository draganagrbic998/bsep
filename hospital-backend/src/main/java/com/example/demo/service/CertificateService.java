package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.utils.Constants;

@Service
public class CertificateService {

	@Autowired
	private RestTemplate restTemplate;
	
	public void sendCertificateRequest(CertificateRequestDTO certificateRequestDTO) {
		this.restTemplate.postForEntity(Constants.SUPER_ADMIN_BACKEND + "/create_request", certificateRequestDTO, CertificateRequestDTO.class).getBody();
	}

}
