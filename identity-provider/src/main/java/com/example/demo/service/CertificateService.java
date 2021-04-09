package com.example.demo.service;

import java.security.cert.X509Certificate;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CertificateService {

	private static final String CERTIFICATES_API = "https://localhost:8080/api/certificates/validate/";

	private final RestTemplate restTemplate;
	
	public boolean validateCertificate(X509Certificate certificate) {
		return this.restTemplate
				.getForEntity(CERTIFICATES_API + certificate.getSerialNumber().longValue(), Boolean.class)
				.getBody().booleanValue();
	}

}
