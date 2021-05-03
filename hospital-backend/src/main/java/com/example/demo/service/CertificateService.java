package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.security.cert.X509Certificate;
import java.util.Base64;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.model.enums.CertificateType;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.utils.AuthenticationProvider;
import com.example.demo.utils.Constants;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

import com.example.demo.dto.CertificateDTO;

@Service
@AllArgsConstructor
public class CertificateService {

	public final static String CERTIFICATES_FOLDER = Constants.RESOURCES_FOLDER + "certificates" + File.separator;
	private final static String CERTIFICATES_API = Constants.ADMIN_BACKEND + "/api/requests";

	private final KeyStoreService keyStoreService;
	private final RestTemplate restTemplate;
	private final AuthenticationProvider authProvider;
	private final Logger logger;

	public void create(CertificateDTO certificateDTO) {
		try {
			String fileName = CERTIFICATES_FOLDER + certificateDTO.getIssuerAlias() + "_" + certificateDTO.getAlias() + ".jks";
			FileOutputStream out = new FileOutputStream(fileName);
			out.write(Base64.getDecoder().decode(certificateDTO.getCertificate()));
			out.close();

			if (certificateDTO.getType().equals(CertificateType.HOSPITAL_DEVICE))
				this.keyStoreService.updateTruststore(certificateDTO.getAlias(), fileName);
			
			this.logger.write(LogStatus.SUCCESS, String.format("Certificate with alias %s successfully saved.", certificateDTO.getAlias()));
		} 
		catch (Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occurred while saving certificate with alias %s.", certificateDTO.getAlias()));
			throw new RuntimeException(e);
		}

	}

	public void request(CertificateRequestDTO requestDTO) {
		try {
			requestDTO.setPath(Constants.BACKEND + "/api/certificates");
			this.restTemplate.exchange(
					CERTIFICATES_API, 
					HttpMethod.POST, 
					this.authProvider.getAuthEntity(requestDTO), 
					CertificateRequestDTO.class);
			this.logger.write(LogStatus.SUCCESS, String.format("Request for certificate with %s alias successfully sent.", requestDTO.getAlias()));
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occurred while sending request for certificate with %s alias.", requestDTO.getAlias()));
			throw e;
		}
	}

	public void revoke(String fileName) {
		try {
			long serial = ((X509Certificate) this.keyStoreService.readCertificate(
					CERTIFICATES_FOLDER + fileName, fileName.split("_")[1].replace(".jks", ""))).getSerialNumber().longValue();		
			this.restTemplate.exchange(
					CERTIFICATES_API + "/" + serial, 
					HttpMethod.DELETE, 
					this.authProvider.getAuthEntity(null), 
					Void.class);
			this.logger.write(LogStatus.SUCCESS, String.format("Revoke request for certificate with %s alias successfully sent.", fileName.split("_")[1]));
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occurred while sending revoke request for certificate with %s alias.", fileName.split("_")[1]));
			throw e;
		}
	}

	public boolean isCertificateValid(long serial) {
		try {
			boolean response = this.restTemplate.exchange(
					CERTIFICATES_API + "/" + serial, 
					HttpMethod.GET, 
					this.authProvider.getAuthEntity(null), 
					Boolean.class).getBody();
			this.logger.write(LogStatus.SUCCESS, String.format("Certificate with serial number %d is %s.", serial, response ? "valid" : "not valid"));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occurred while checking for validity of certificate with %d serial number.", serial));
			throw e;
		}
	}

}
