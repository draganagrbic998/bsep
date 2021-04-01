package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.utils.Constants;
import com.example.demo.dto.CertificateDTO;

@Service
public class CertificateService {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final String CERTIFICATES_PATH = Constants.ADMIN_BACKEND + "/api/certificates/requests";

	public void save(CertificateDTO certificateDTO) {
		byte[] decryptedCertificate = Base64.getDecoder().decode(certificateDTO.getCertificate());
		String fileName = certificateDTO.getIssuerAlias() + "_" + certificateDTO.getAlias() + "_" + certificateDTO.getOrganizationUnit() + ".jks";

		try {
			File file = ResourceUtils.getFile("classpath:certificates" + File.separatorChar + fileName);
			FileOutputStream out = new FileOutputStream(file);
			out.write(decryptedCertificate);
			out.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void sendRequest(CertificateRequestDTO requestDTO) {
		requestDTO.setPath(Constants.BACKEND + "/api/certificates");
		this.restTemplate.postForEntity(CERTIFICATES_PATH, requestDTO, CertificateRequestDTO.class);
	}

}
