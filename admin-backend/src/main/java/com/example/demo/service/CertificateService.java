package com.example.demo.service;

import com.example.demo.dto.certificate.CreateCertificateDTO;
import com.example.demo.dto.certificate.CreatedCertificateDTO;
import com.example.demo.exception.AliasExistsException;
import com.example.demo.exception.CertificateAuthorityException;
import com.example.demo.exception.InvalidIssuerException;
import com.example.demo.model.*;
import com.example.demo.repository.CertificateInfoRepository;
import com.example.demo.utils.CertificateGenerator;
import com.example.demo.utils.CertificateUtils;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;

@Service
@AllArgsConstructor
public class CertificateService {

	private final CertificateInfoRepository certificateRepository;
	private final CertificateRequestService requestService;
	private final CertificateValidationService validationService;
	private final CertificateGenerator certificateGenerator;
	private final KeyStoreService keyStoreService;
	private final RestTemplate restTemplate;
	private final EmailService emailService;

	public void create(CreateCertificateDTO certificateDTO) {
		this.keyStoreService.loadKeyStore();
		String issuerAlias = certificateDTO.getIssuerAlias();
		Certificate[] issuerChain = this.keyStoreService.readCertificateChain(issuerAlias);
		IssuerData issuerData = this.keyStoreService.readIssuerFromStore(issuerAlias);
		X509Certificate issuer = (X509Certificate) issuerChain[0];
		CertificateInfo issuerInfo = this.certificateRepository.findByAlias(issuerAlias);
		
		if (!this.validationService.isCertificateValid(issuerAlias))
			throw new InvalidIssuerException();

		if (this.certificateRepository.findByAlias(certificateDTO.getAlias()) != null)
			throw new AliasExistsException();

		try {
			if (issuer.getBasicConstraints() == -1 || !issuer.getKeyUsage()[5]) {
				throw new CertificateAuthorityException();
			}
		} 
		catch (NullPointerException ignored) {
		}

		SubjectData subjectData = new SubjectData();
		subjectData.setX500name(CertificateUtils.certificateNameFromData(certificateDTO));
		Date[] dates = CertificateUtils.generateDates(certificateDTO.getTemplate().equals(Template.SUB_CA) ? 24 : 12);
		subjectData.setStartDate(dates[0]);
		subjectData.setEndDate(dates[1]);
		KeyPair keyPair = CertificateUtils.generateKeyPair();
		subjectData.setPublicKey(keyPair.getPublic());
		
		CertificateInfo cert = generateCertificate(subjectData, issuerInfo, certificateDTO);
		subjectData.setSerialNumber(cert.getId() + "");
		X509Certificate certificate = this.certificateGenerator.generateCertificate(
				subjectData, issuerData, keyPair, issuerChain[0], false, 
				certificateDTO.getExtensions());
		Certificate[] chain = ArrayUtils.insert(0, issuerChain, certificate);

		this.keyStoreService.savePrivateKey(certificateDTO.getAlias(), chain, keyPair.getPrivate());
		this.keyStoreService.saveKeyStore();
		this.keyStoreService.addToTruststore(issuerInfo, cert, certificate, 
			this.keyStoreService.saveSeparateKeys(issuerInfo, cert, keyPair.getPrivate(), chain));
		
		String fileName = issuerInfo.getAlias() + "_" + cert.getAlias() + "_" + cert.getOrganizationUnit();
		byte[] returnValue = null;

		try {
			InputStream in = new FileInputStream(Constants.CERTIFICATES_FOLDER + fileName + ".jks");
			returnValue = IOUtils.toByteArray(in);
			in.close();
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (certificateDTO.getId() != 0) {
			CertificateRequest request = this.requestService.findOne(certificateDTO.getId());
			CreatedCertificateDTO created = new CreatedCertificateDTO(
				issuerInfo.getAlias(), cert.getAlias(), cert.getOrganizationUnit(),
				request.getType(), Base64.getEncoder().encodeToString(returnValue)
			);

			this.restTemplate.postForEntity(request.getPath(), created, CreatedCertificateDTO.class).getBody();
			this.requestService.delete(certificateDTO.getId());
			
			String certFileName = cert.getIssuerAlias() + "_" + cert.getAlias() + "_" + cert.getOrganizationUnit() + ".jks";
			String location = request.getPath().split("//")[1].split("/")[0];
			this.emailService.sendInfoMail(cert.getEmail(), certFileName, location, "Certificate Issued - Bezbednost", Constants.ISSUED_TEMPLATE);
		}

	}

	public CertificateInfo generateCertificate(SubjectData subjectData, CertificateInfo issuer, CreateCertificateDTO certificateDTO) {
		CertificateInfo certificate = new CertificateInfo();
		certificate.setIssuer(issuer);
		certificate.setAlias(certificateDTO.getAlias());
		certificate.setCommonName(subjectData.getX500name().getRDNs(BCStyle.CN)[0].getFirst().getValue().toString());
		certificate.setOrganizationUnit(certificateDTO.getOrganizationUnit());
		certificate.setOrganization(certificateDTO.getOrganization());
		certificate.setCountry(certificateDTO.getCountry());
		certificate.setEmail(certificateDTO.getEmail());
		certificate.setTemplate(certificateDTO.getTemplate());
		certificate.setStartDate(subjectData.getStartDate());
		certificate.setEndDate(subjectData.getEndDate());
		certificate.setExtensions(certificateDTO.getExtensions());
		return this.certificateRepository.save(certificate);
	}

}

