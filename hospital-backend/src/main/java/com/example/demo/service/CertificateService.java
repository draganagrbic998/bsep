package com.example.demo.service;

import java.io.FileOutputStream;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.model.CertificateType;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

import com.example.demo.dto.CertificateDTO;

@Service
@AllArgsConstructor
public class CertificateService {

	private static final String CERTIFICATES_API = Constants.ADMIN_BACKEND + "/api/certificates/";
	private static final String REQUESTS_API = Constants.ADMIN_BACKEND + "/api/requests/";

	private final KeyStoreService keyStoreService;
	private final RestTemplate restTemplate;

	public void save(CertificateDTO certificateDTO) {
		byte[] decryptedCertificate = Base64.getDecoder().decode(certificateDTO.getCertificate());
		String fileName = certificateDTO.getIssuerAlias() + "_" + certificateDTO.getAlias() + "_" + certificateDTO.getOrganizationUnit() + ".jks";

		try {
			FileOutputStream out = new FileOutputStream(Constants.CERTIFICATES_FOLDER + fileName);
			out.write(decryptedCertificate);
			out.close();

			if (certificateDTO.getType().equals(CertificateType.HOSPITAL_DEVICE))
				this.keyStoreService.updateTruststore(certificateDTO.getAlias(), Constants.CERTIFICATES_FOLDER + fileName);
			
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void sendRequest(CertificateRequestDTO requestDTO) {
		requestDTO.setPath(Constants.BACKEND + "/api/certificates");
		this.restTemplate.postForEntity(REQUESTS_API, requestDTO, CertificateRequestDTO.class);
	}

	public void sendRevokeRequest(String certFileName) {
		X509Certificate certificate = (X509Certificate) this.keyStoreService
				.readCertificate(Constants.CERTIFICATES_FOLDER + certFileName, certFileName.split("_")[1]);
		this.restTemplate.getForEntity(CERTIFICATES_API + "/revoke/" + certificate.getSerialNumber().longValue(), Void.class);
	}

	public boolean validateCertificate(X509Certificate certificate) {
		return this.restTemplate
				.getForEntity(CERTIFICATES_API + "validate/" + certificate.getSerialNumber().longValue(), Boolean.class)
				.getBody().booleanValue();
	}

}
