package com.example.demo.service;

import java.io.FileOutputStream;
import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CertificateDTO;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CertificateService {

	private final RestTemplate restTemplate;

	public void save(CertificateDTO certificateDTO) {
		byte[] decryptedCertificate = Base64.getDecoder().decode(certificateDTO.getCertificate());
		String fileName = certificateDTO.getIssuerAlias() + "_" + certificateDTO.getAlias() + "_"
				+ certificateDTO.getOrganizationUnit() + ".jks";

		try {
			FileOutputStream out = new FileOutputStream(Constants.CERTIFICATES_FOLDER + fileName);
			out.write(decryptedCertificate);
			out.close();
			this.restTemplate.postForEntity(Constants.HOSPITAL_API, certificateDTO, CertificateDTO.class);
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
