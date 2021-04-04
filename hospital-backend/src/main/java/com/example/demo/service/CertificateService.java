package com.example.demo.service;

import java.io.FileOutputStream;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.dto.RevokeRequestDTO;
import com.example.demo.dto.ValidationRequestDTO;
import com.example.demo.model.CertificateType;
import com.example.demo.utils.Constants;
import com.example.demo.dto.CertificateDTO;

@Service
public class CertificateService {

	@Autowired
	private KeyStoreService keyStoreService;

	@Autowired
	private RestTemplate restTemplate;

	public void save(CertificateDTO certificateDTO) {
		byte[] decryptedCertificate = Base64.getDecoder().decode(certificateDTO.getCertificate());
		String fileName = certificateDTO.getIssuerAlias() + "_" + certificateDTO.getAlias() + "_"
				+ certificateDTO.getOrganizationUnit() + ".jks";

		try {
			FileOutputStream out = new FileOutputStream(Constants.CERTIFICATES_FOLDER + fileName);
			out.write(decryptedCertificate);
			out.close();

			if (CertificateType.valueOf(certificateDTO.getType()) == CertificateType.HOSPITAL_DEVICE)
				this.keyStoreService.updateTruststore(certificateDTO.getAlias(),
						Constants.CERTIFICATES_FOLDER + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendRequest(CertificateRequestDTO requestDTO) {
		requestDTO.setPath(Constants.BACKEND + "/api/certificates");
		this.restTemplate.postForEntity(Constants.CERTIFICATES_PATH + "requests", requestDTO,
				CertificateRequestDTO.class);
	}

	public void sendRevokeRequest(String certFileName) {
		X509Certificate cert = (X509Certificate) this.keyStoreService
				.readCertificate(Constants.CERTIFICATES_FOLDER + certFileName, certFileName.split("_")[1]);
		RevokeRequestDTO requestDTO = new RevokeRequestDTO();
		requestDTO.setSerial(cert.getSerialNumber().longValue());
		requestDTO.setPath(Constants.BACKEND);
		this.restTemplate.postForEntity(Constants.CERTIFICATES_PATH + "requests/revoke", requestDTO,
				RevokeRequestDTO.class);
	}

	public boolean validateClientCertificate(X509Certificate clientCertificate) {
		ValidationRequestDTO validationRequestDTO = new ValidationRequestDTO();
		validationRequestDTO.setSerial(clientCertificate.getSerialNumber().longValue());
		validationRequestDTO.setPath(Constants.BACKEND);
		return this.restTemplate
				.postForEntity(Constants.CERTIFICATES_PATH + "validate", validationRequestDTO, Boolean.class).getBody()
				.booleanValue();
	}

}
