package com.example.demo.service;

import java.io.FileOutputStream;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.model.CertificateType;
import com.example.demo.utils.AuthenticationProvider;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

import com.example.demo.dto.CertificateDTO;

@Service
@AllArgsConstructor
public class CertificateService {

	private static final String CERTIFICATES_API = Constants.ADMIN_BACKEND + "/api/requests";

	private final KeyStoreService keyStoreService;
	private final RestTemplate restTemplate;
	private final AuthenticationProvider authProvider;

	public void create(CertificateDTO certificateDTO) {
		try {
			byte[] decryptedCertificate = Base64.getDecoder().decode(certificateDTO.getCertificate());
			String fileName = Constants.CERTIFICATES_FOLDER + certificateDTO.getIssuerAlias() + "_" + certificateDTO.getAlias() + ".jks";

			FileOutputStream out = new FileOutputStream(fileName);
			out.write(decryptedCertificate);
			out.close();

			if (certificateDTO.getType().equals(CertificateType.HOSPITAL_DEVICE))
				this.keyStoreService.updateTruststore(certificateDTO.getAlias(), fileName);
			
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public boolean isCertificateValid(long serial) {
		return this.restTemplate.exchange(
				CERTIFICATES_API + "/" + serial, 
				HttpMethod.GET, 
				this.authProvider.getAuthEntity(null), 
				Boolean.class).getBody();
	}

	public void request(CertificateRequestDTO requestDTO) {
		requestDTO.setPath(Constants.BACKEND + "/api/certificates");
		this.restTemplate.exchange(
				CERTIFICATES_API, 
				HttpMethod.POST, 
				this.authProvider.getAuthEntity(requestDTO), 
				CertificateRequestDTO.class);
	}

	public void revoke(String fileName) {
		X509Certificate certificate = (X509Certificate) this.keyStoreService
				.readCertificate(Constants.CERTIFICATES_FOLDER + fileName, fileName.split("_")[1]);
		
		this.restTemplate.exchange(
				CERTIFICATES_API + "/" + certificate.getSerialNumber().longValue(), 
				HttpMethod.DELETE, 
				this.authProvider.getAuthEntity(null), 
				Void.class);
	}

}
