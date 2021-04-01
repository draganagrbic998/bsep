package com.example.demo.service;

import java.io.FileOutputStream;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.dto.RevokeRequestDTO;
import com.example.demo.utils.Constants;
import com.example.demo.dto.CertificateDTO;

@Service
public class CertificateService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private KeyStoreService keystoreService;

	public void save(CertificateDTO certificateDTO) {
		byte[] decryptedCertificate = Base64.getDecoder().decode(certificateDTO.getCertificate());
		String fileName = certificateDTO.getIssuerAlias() + "_" + certificateDTO.getAlias() + "_"
				+ certificateDTO.getOrganizationUnit() + ".jks";

		try {
			FileOutputStream out = new FileOutputStream(Constants.CERTIFICATES_FOLDER + fileName);
			out.write(decryptedCertificate);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendRequest(CertificateRequestDTO requestDTO) {
		requestDTO.setPath("https://" + requestDTO.getPath() + "/api/certificates");
		this.restTemplate.postForEntity(Constants.CERTIFICATES_PATH, requestDTO, CertificateRequestDTO.class);
	}

	public void sendRevokeRequest(String certFileName) {
		X509Certificate cert = (X509Certificate) keystoreService
				.readCertificate(Constants.CERTIFICATES_FOLDER + certFileName);

		RevokeRequestDTO revokeRequestDTO = new RevokeRequestDTO();
		revokeRequestDTO.setSerial(cert.getSerialNumber().longValue());
		revokeRequestDTO.setPath(Constants.BACKEND);
		this.restTemplate.postForEntity(Constants.CERTIFICATES_PATH + "/revoke", revokeRequestDTO,
				RevokeRequestDTO.class);

	}

}
