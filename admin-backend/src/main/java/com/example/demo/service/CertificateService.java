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
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
public class CertificateService {

	private final CertificateInfoRepository certificateInfoRepository;
	private final CertificateRequestService certificateRequestService;
	private final CertificateValidationService certificateValidationService;
	private final CertificateGenerator certificateGenerator;
	private final KeyStoreService keyStoreService;
	private final RestTemplate restTemplate;
	private final EmailService emailService;

	public void create(CreateCertificateDTO certificateDTO) {
		this.keyStoreService.loadKeyStore();
		String issuerAlias = certificateDTO.getIssuerAlias();
		Certificate[] issuerCertificateChain = this.keyStoreService.readCertificateChain(issuerAlias);
		IssuerData issuerData = this.keyStoreService.readIssuerFromStore(issuerAlias);
		X509Certificate issuer = (X509Certificate) issuerCertificateChain[0];
		CertificateInfo issuerInfo = this.certificateInfoRepository.findByAliasIgnoreCase(issuerAlias);
		
		if (!this.certificateValidationService.isCertificateValid(issuerAlias))
			throw new InvalidIssuerException();

		try {
			if (issuer.getBasicConstraints() == -1 || !issuer.getKeyUsage()[5]) {
				throw new CertificateAuthorityException();
			}
		} 
		catch (NullPointerException ignored) {
		}

		String alias = certificateDTO.getAlias();
		if (this.certificateInfoRepository.findByAliasIgnoreCase(alias) != null)
			throw new AliasExistsException();

		KeyPair keyPair = CertificateUtils.generateKeyPair();
		if (keyPair == null)
			return;

		X500Name subjectName = CertificateUtils.certificateNameFromData(certificateDTO);
		SubjectData subjectData = new SubjectData();
		subjectData.setX500name(subjectName);
		String templateString = certificateDTO.getTemplate();

		Date[] dates;
		dates = CertificateUtils.generateDates(templateString.equals("SUB_CA") ? 24 : 12);
		subjectData.setStartDate(dates[0]);
		subjectData.setEndDate(dates[1]);
		subjectData.setPublicKey(keyPair.getPublic());
		Template template = Template.valueOf(templateString);

		CertificateInfo certInfo = generateCertificateInfo(subjectData, certificateDTO.getIssuerAlias(),
				certificateDTO.getAlias(), certificateDTO.getCountry(),
				certificateDTO.getOrganizationUnit(), certificateDTO.getOrganization(),
				certificateDTO.getEmail(), template == Template.SUB_CA, template, certificateDTO.isBasicConstraints(),
				certificateDTO.getExtendedKeyUsage(), certificateDTO.getKeyUsage());

		issuerInfo.addIssued(certInfo);
		this.certificateInfoRepository.save(issuerInfo);
		subjectData.setSerialNumber(certInfo.getId().toString());

		X509Certificate createdCertificate = this.certificateGenerator.generateCertificate(
				subjectData, issuerData, template, keyPair, false, issuerCertificateChain[0], 
				certificateDTO.isBasicConstraints(), certificateDTO.getExtendedKeyUsage(), certificateDTO.getKeyUsage());
		Certificate[] newCertificateChain = ArrayUtils.insert(0, issuerCertificateChain, createdCertificate);

		this.keyStoreService.savePrivateKey(certificateDTO.getAlias(), newCertificateChain, keyPair.getPrivate());
		this.keyStoreService.saveKeyStore();
		String filename = this.keyStoreService.saveSeparateKeys(issuerInfo, certInfo, keyPair.getPrivate(), newCertificateChain);
		this.keyStoreService.addToTruststore(issuerInfo, certInfo, createdCertificate, filename);
		String fileName = issuerInfo.getAlias() + "_" + certInfo.getAlias() + "_" + certInfo.getOrganizationUnit();
		byte[] returnValue = null;

		try {
			InputStream in = new FileInputStream("./src/main/resources/" + Constants.CERTIFICATES_FOLDER + fileName + ".jks");
			returnValue = IOUtils.toByteArray(in);
			in.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

		if (certificateDTO.getId() != 0) {
			try {
				CreatedCertificateDTO dto = new CreatedCertificateDTO();
				dto.setIssuerAlias(issuerInfo.getAlias());
				dto.setAlias(certInfo.getAlias());
				dto.setOrganizationUnit(certInfo.getOrganizationUnit());
				dto.setCertificate(Base64.getEncoder().encodeToString(returnValue));
				CertificateRequest request = this.certificateRequestService.findOne(certificateDTO.getId());
				dto.setType(request.getType().name());

				this.restTemplate.postForEntity(request.getPath(), dto, CreatedCertificateDTO.class).getBody();
				this.certificateRequestService.delete(certificateDTO.getId());

				String certFileName = certInfo.getIssuerAlias() + "_" + certInfo.getAlias() + "_" + certInfo.getOrganizationUnit() + ".jks";
				String location = request.getPath().split("//")[1].split("/")[0];
				this.emailService.sendInfoMail(certInfo.getEmail(), certFileName, location, "Certificate Issued - Bezbednost", Constants.ISSUED_TEMPLATE);
			} 
			catch (RestClientException | IllegalArgumentException | MessagingException e) {
				e.printStackTrace();
			}
		}

	}

	public CertificateInfo generateCertificateInfo(SubjectData subjectData, String issuerAlias, String alias,
			String country, String organizationUnit, String organization, String email, boolean isCA,
			Template template, boolean isBasic, String extentedKeyUsage, List<String> keyUsages) {
		
		CertificateInfo certInfo = new CertificateInfo();
		String cn = subjectData.getX500name().getRDNs(BCStyle.CN)[0].getFirst().getValue().toString();

		certInfo.setAlias(alias);
		certInfo.setCommonName(cn);
		certInfo.setOrganizationUnit(organizationUnit);
		certInfo.setOrganization(organization);
		certInfo.setCountry(country);
		certInfo.setEmail(email);
		certInfo.setTemplate(template);
		certInfo.setBasicConstraints(isBasic);
		certInfo.setKeyUsage(keyUsages.stream().reduce("", (subtotal, element) -> subtotal +","+ element));
		certInfo.setExtendedKeyUsage(extentedKeyUsage);
		certInfo.setIssuerAlias(issuerAlias);
		certInfo.setCA(isCA);
		certInfo.setStartDate(subjectData.getStartDate());
		certInfo.setEndDate(subjectData.getEndDate());
		return this.certificateInfoRepository.save(certInfo);
	}

}
