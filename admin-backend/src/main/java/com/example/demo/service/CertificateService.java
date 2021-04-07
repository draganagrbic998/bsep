package com.example.demo.service;

import com.example.demo.dto.certificate.CreateCertificateDTO;
import com.example.demo.dto.certificate.CreatedCertificateDTO;
import com.example.demo.exception.AliasExistsException;
import com.example.demo.exception.CertificateAuthorityException;
import com.example.demo.exception.CertificateNotFoundException;
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
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
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

	private final KeyStoreService keyStoreService;
	private final CertificateInfoRepository certificateInfoRepository;
	private final CertificateGenerator certificateGenerator;
	private final RestTemplate restTemplate;
	private final EmailService emailService;
	private final CertificateRequestService certificateRequestService;
	private final CertificateValidationService certificateValidationService;

	public void create(CreateCertificateDTO createCertificateDto) {
		this.keyStoreService.loadKeyStore();

		String issuerAlias = createCertificateDto.getIssuerAlias();

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

		String alias = createCertificateDto.getAlias();

		if (this.certificateInfoRepository.findByAliasIgnoreCase(alias) != null)
			throw new AliasExistsException();

		KeyPair keyPair = CertificateUtils.generateKeyPair();

		if (keyPair == null)
			return;

		X500Name subjectName = CertificateUtils.certificateNameFromData(createCertificateDto);

		SubjectData subjectData = new SubjectData();
		subjectData.setX500name(subjectName);

		String templateString = createCertificateDto.getTemplate();

		Date[] dates;
		dates = CertificateUtils.generateDates(12);
		subjectData.setStartDate(dates[0]);
		subjectData.setEndDate(dates[1]);
		subjectData.setPublicKey(keyPair.getPublic());
		boolean isCa;
		Template template;

		if (templateString != null) {
			template = Template.valueOf(templateString);
			isCa = template == Template.SUB_CA;
		} else {
			isCa = false;
			template = null;
		}

		CertificateInfo certInfo = generateCertificateInfo(subjectData, createCertificateDto.getIssuerAlias(),
				createCertificateDto.getAlias(), createCertificateDto.getCountry(),
				createCertificateDto.getOrganizationUnit(), createCertificateDto.getOrganization(),
				createCertificateDto.getEmail(), isCa, template,
				createCertificateDto.getExtensions());

		issuerInfo.addIssued(certInfo);
		this.certificateInfoRepository.save(issuerInfo);

		subjectData.setSerialNumber(certInfo.getId().toString());

		//ovo proveri jel ok
		X509Certificate createdCertificate = this.certificateGenerator.generateCertificate(subjectData, issuerData,
				keyPair, false, issuerCertificateChain[0], createCertificateDto.getExtensions());

		Certificate[] newCertificateChain = ArrayUtils.insert(0, issuerCertificateChain, createdCertificate);

		this.keyStoreService.savePrivateKey(createCertificateDto.getAlias(), newCertificateChain, keyPair.getPrivate());
		this.keyStoreService.saveKeyStore();

		String filename = this.keyStoreService.saveSeparateKeys(issuerInfo, certInfo, keyPair.getPrivate(), newCertificateChain);
		this.keyStoreService.addToTruststore(issuerInfo, certInfo, createdCertificate, filename);

		byte[] returnValue = null;
		String fileName = issuerInfo.getAlias() + "_" + certInfo.getAlias() + "_" + certInfo.getOrganizationUnit();
		InputStream in = null;

		try {
			in = new FileInputStream("./src/main/resources/" + Constants.CERTIFICATES_FOLDER + fileName + ".jks");
			returnValue = IOUtils.toByteArray(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (createCertificateDto.getId() != 0) {
			try {
				CreatedCertificateDTO dto = new CreatedCertificateDTO();
				dto.setIssuerAlias(issuerInfo.getAlias());
				dto.setAlias(certInfo.getAlias());
				dto.setOrganizationUnit(certInfo.getOrganizationUnit());
				dto.setCertificate(Base64.getEncoder().encodeToString(returnValue));

				CertificateRequest request = this.certificateRequestService.findOne(createCertificateDto.getId());
				dto.setType(request.getType().name());

				this.restTemplate.postForEntity(request.getPath(), dto, CreatedCertificateDTO.class).getBody();

				this.certificateRequestService.delete(createCertificateDto.getId());

				String certFileName = certInfo.getIssuerAlias() + "_" + certInfo.getAlias() + "_"
						+ certInfo.getOrganizationUnit() + ".jks";
				
				String location = request.getPath().split("//")[1].split("/")[0];
				this.emailService.sendInfoMail(certInfo.getEmail(), certFileName, location,
						"Certificate Issued - Bezbednost", Constants.ISSUED_TEMPLATE);
			} catch (RestClientException | IllegalArgumentException | MessagingException e) {
				e.printStackTrace();
			}
		}

	}

	public CertificateInfo generateCertificateInfo(SubjectData subjectData, String issuerAlias, String alias,
			String country, String organizationUnit, String organization, String email, boolean isCA,
			Template template, Extensions extensions) {
		CertificateInfo certInfo = new CertificateInfo();
		String cn = subjectData.getX500name().getRDNs(BCStyle.CN)[0].getFirst().getValue().toString();

		certInfo.setAlias(alias);
		certInfo.setCommonName(cn);
		certInfo.setIssuerAlias(issuerAlias);
		certInfo.setStartDate(subjectData.getStartDate());
		certInfo.setEndDate(subjectData.getEndDate());
		certInfo.setRevoked(false);
		certInfo.setCountry(country);
		certInfo.setOrganizationUnit(organizationUnit);
		certInfo.setOrganization(organization);
		certInfo.setEmail(email);
		certInfo.setRevocationReason("");
		certInfo.setCA(isCA);
		certInfo.setTemplate(template);
		certInfo.setExtensions(extensions);
		return this.certificateInfoRepository.save(certInfo);
	}

	public String getCrt(String alias) throws IOException {
		Certificate[] chain = this.keyStoreService.readCertificateChain(alias);

		StringBuilder chainBuilder = new StringBuilder();
		for (Certificate c : chain) {
			String pemCertificate = this.writePem(c);
			chainBuilder.append(pemCertificate);
		}
		return chainBuilder.toString();
	}

	public String getKey(String alias) throws IOException {
		PrivateKey privateKey = this.keyStoreService.readPrivateKey(alias);
		PemObject pemFile = new PemObject("PRIVATE KEY", privateKey.getEncoded());
		return this.writePem(pemFile);
	}

	private String writePem(Object obj) throws IOException {
		StringWriter writer = new StringWriter();
		JcaPEMWriter pemWriter = new JcaPEMWriter(writer);
		pemWriter.writeObject(obj);
		pemWriter.flush();
		pemWriter.close();
		return writer.toString();
	}
}
