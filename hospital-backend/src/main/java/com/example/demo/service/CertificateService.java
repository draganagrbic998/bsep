package com.example.demo.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.dto.CreatedCertificateDTO;

@Service
public class CertificateService {

	@Autowired
	private RestTemplate restTemplate;

	public void sendCertificateRequest(CertificateRequestDTO certificateRequestDTO) {
		/*
		 * ovo ce da se kasnije sifruje pre slanja treba nam da posaljemo da bi bekend
		 * od admina znao gde da posalje gotov sertifikat. Ili ovako ili da superadmina
		 * pamti kod sebe sve putanje od svih bolnica (ako neko ima bolji predlog
		 * recite)
		 */
		certificateRequestDTO.setPath("https://localhost:8081/api/certificates");

		this.restTemplate.postForEntity("https://localhost:8080/api/certificates/requests/create",
				certificateRequestDTO, CertificateRequestDTO.class).getBody();
	}

	public void saveCertificate(CreatedCertificateDTO createdCertificateDTO) {
		byte[] decryptedCertificate = Base64.getDecoder().decode(createdCertificateDTO.getCertificate());
		String filename = createdCertificateDTO.getIssuerAlias() + "_" + createdCertificateDTO.getAlias() + "_"
				+ createdCertificateDTO.getOrganizationUnit() + ".jks";

		FileOutputStream out = null;
		try {
			out = new FileOutputStream("./src/main/resources/created-certificates/" + filename);
			out.write(decryptedCertificate);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
