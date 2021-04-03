package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CertificateDTO;

@Service
public class CertificateService {

	@Autowired
	private RestTemplate restTemplate;

	private final static String HOSPITAL_API = "https://localhost:8081/api/certificates";

	public void save(CertificateDTO certificateDTO) {
		byte[] decryptedCertificate = Base64.getDecoder().decode(certificateDTO.getCertificate());
		String fileName = certificateDTO.getIssuerAlias() + "_" + certificateDTO.getAlias() + "_"
				+ certificateDTO.getOrganizationUnit() + ".jks";

		try {
			File file = ResourceUtils.getFile("classpath:certificates" + File.separatorChar + fileName);
			file.createNewFile();
			FileOutputStream fout = new FileOutputStream(file);
			fout.write(decryptedCertificate);
			fout.close();
			this.restTemplate.postForEntity(HOSPITAL_API, certificateDTO, CertificateDTO.class);
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
