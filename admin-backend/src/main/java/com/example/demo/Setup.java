package com.example.demo;

import com.example.demo.model.CertificateInfo;
import com.example.demo.model.IssuerData;
import com.example.demo.model.SubjectData;
import com.example.demo.model.Template;
import com.example.demo.repository.CertificateInfoRepository;
import com.example.demo.service.KeyStoreService;
import com.example.demo.utils.CertificateGenerator;
import com.example.demo.utils.CertificateUtils;

import lombok.AllArgsConstructor;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class Setup implements ApplicationRunner {

	private final KeyStoreService keyStoreService;
	private final CertificateInfoRepository certificateInfoRepository;
	private final CertificateGenerator certificateGenerator;
	
	@Override
	public void run(ApplicationArguments args) {
		this.keyStoreService.loadKeyStore();
		Certificate root = this.keyStoreService.readCertificate("root");
		CertificateInfo certificateInfo = this.certificateInfoRepository.findByAliasIgnoreCase("root");
		if (root == null || certificateInfo == null) {
			this.createRootCA();
			this.keyStoreService.saveKeyStore();
		}
	}

	private void createRootCA() {
		
		
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, "root");
		builder.addRDN(BCStyle.O, "BSEP");
		builder.addRDN(BCStyle.OU, "CA");
		builder.addRDN(BCStyle.C, "RS");
		builder.addRDN(BCStyle.E, "tim1@email.com");
		X500Name rootName = builder.build();
		KeyPair keyPair = CertificateUtils.generateKeyPair();
		SubjectData subjectData = new SubjectData();
		IssuerData issuerData = new IssuerData();

		issuerData.setX500name(rootName);
		issuerData.setPrivateKey(keyPair.getPrivate());

		subjectData.setX500name(rootName);
		subjectData.setPublicKey(keyPair.getPublic());

		Date[] dates = CertificateUtils.generateDates(120);
		subjectData.setStartDate(dates[0]);
		subjectData.setEndDate(dates[1]);

		CertificateInfo certificateInfo = generateCertificateInfoEntity(subjectData);
		subjectData.setSerialNumber(certificateInfo.getId().toString());
		
		//ovo proveri jel ok
		X509Certificate rootCertificate = this.certificateGenerator.generateCertificate(subjectData, issuerData,
				Template.SUB_CA, keyPair, true, null, true, null, List.of("cRLSign", "digitalSignature", "keyCertSign"));
				

		this.keyStoreService.savePrivateKey("root", new Certificate[] { rootCertificate }, keyPair.getPrivate());
		

	}

	private CertificateInfo generateCertificateInfoEntity(SubjectData subjectData) {		
		CertificateInfo certInfo = new CertificateInfo();
		certInfo.setCommonName("root");
		certInfo.setAlias("root");
		certInfo.setStartDate(subjectData.getStartDate());
		certInfo.setEndDate(subjectData.getEndDate());
		certInfo.setRevoked(false);
		certInfo.setRevocationReason("");
		certInfo.setOrganization("BSEP");
		certInfo.setCountry("RS");
		certInfo.setEmail("tim1@email.com");
		certInfo.setOrganizationUnit("CA");
		certInfo.setIssuerAlias("root");
		certInfo.setCA(true);
		certInfo.setTemplate(Template.SUB_CA);
		certInfo.setBasicConstraints(true);
		certInfo.setKeyUsage("cRLSign, digitalSignature, keyCertSign");
		return this.certificateInfoRepository.save(certInfo);
	}
}
