package com.example.demo.service;

import com.example.demo.dto.CreateCertificateDTO;
import com.example.demo.exception.AliasExistsException;
import com.example.demo.exception.CertificateAuthorityException;
import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.exception.InvalidIssuerException;
import com.example.demo.model.CertificateInfo;
import com.example.demo.model.IssuerData;
import com.example.demo.model.SubjectData;
import com.example.demo.model.Template;
import com.example.demo.repository.CertificateInfoRepository;
import com.example.demo.utils.CertificateGenerator;

import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

@Service
public class CertificateService {

	@Autowired
	private KeyStoreService keyStoreService;

	@Autowired
	private CertificateInfoRepository certificateInfoRepository;

	@Autowired
	private CertificateGenerator certificateGenerator;

	public CertificateInfo createCertificate(CreateCertificateDTO createCertificateDto)
			throws CertificateNotFoundException, InvalidIssuerException, CertificateAuthorityException,
			AliasExistsException {
		keyStoreService.loadKeyStore();

		String issuerAlias = createCertificateDto.getIssuerAlias();

		Certificate[] issuerCertificateChain = keyStoreService.readCertificateChain(issuerAlias);
		IssuerData issuerData = keyStoreService.readIssuerFromStore(issuerAlias);

		X509Certificate issuer = (X509Certificate) issuerCertificateChain[0];
		CertificateInfo issuerInfo = certificateInfoRepository.findFirstByAliasContainingIgnoreCase(issuerAlias);
		if (!isCertificateValid(issuerAlias))
			throw new InvalidIssuerException();

		try {
			if (issuer.getBasicConstraints() == -1 || !issuer.getKeyUsage()[5]) {
				// Sertifikat nije CA
				// https://stackoverflow.com/questions/12092457/how-to-check-if-x509certificate-is-ca-certificate
				throw new CertificateAuthorityException();
			}
		} catch (NullPointerException ignored) {
		}

		String alias = createCertificateDto.getAlias();

		if (certificateInfoRepository.findFirstByAliasContainingIgnoreCase(alias) != null)
			throw new AliasExistsException();

		KeyPair keyPair = generateKeyPair();

		if (keyPair == null)
			return null;

		X500Name subjectName = certificateNameFromData(createCertificateDto);

		SubjectData subjectData = new SubjectData();
		subjectData.setX500name(subjectName);

		String templateString = createCertificateDto.getTemplate();

		Date[] dates;
		dates = generateDates(templateString.equals("SUB_CA") ? 24 : 12);
		subjectData.setStartDate(dates[0]);
		subjectData.setEndDate(dates[1]);
		subjectData.setPublicKey(keyPair.getPublic());

		Template template = Template.valueOf(templateString);

		CertificateInfo certInfo = generateCertificateInfo(subjectData, createCertificateDto.getIssuerAlias(),
				createCertificateDto.getAlias(), createCertificateDto.getCountry(),
				createCertificateDto.getOrganizationUnit(), createCertificateDto.getOrganization(),
				createCertificateDto.getEmail(), template == Template.SUB_CA, template);

		issuerInfo.addIssued(certInfo);
		this.certificateInfoRepository.save(issuerInfo);

		subjectData.setSerialNumber(certInfo.getId().toString());

		X509Certificate createdCertificate = this.certificateGenerator.generateCertificate(subjectData, issuerData,
				template, keyPair, false, issuerCertificateChain[0]);

		Certificate[] newCertificateChain = ArrayUtils.insert(0, issuerCertificateChain, createdCertificate);

		keyStoreService.savePrivateKey(createCertificateDto.getAlias(), newCertificateChain, keyPair.getPrivate());

		keyStoreService.saveKeyStore();

		return certInfo;

	}

	public static X500Name certificateNameFromData(CreateCertificateDTO createCertificateDto) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, createCertificateDto.getCommonName());
		builder.addRDN(BCStyle.O, createCertificateDto.getOrganization());
		builder.addRDN(BCStyle.OU, createCertificateDto.getOrganizationUnit());
		builder.addRDN(BCStyle.E, createCertificateDto.getEmail());
		builder.addRDN(BCStyle.C, createCertificateDto.getCountry());
		return builder.build();
	}

	private boolean isCertificateValid(String alias) {
		Certificate[] chain = keyStoreService.readCertificateChain(alias);

		if (chain == null) {
			return false;
		}

		Date now = new Date();
		X509Certificate x509cert;
		for (int i = 0; i < chain.length; i++) {
			x509cert = (X509Certificate) chain[i];

			CertificateInfo certificateInfo = certificateInfoRepository.findById(x509cert.getSerialNumber().longValue())
					.orElse(null);

			if (certificateInfo.isRevoked()) {
				return false;
			}

			if (now.after(x509cert.getNotAfter()) || now.before(x509cert.getNotBefore())) {
				return false;
			}

			try {
				if (i == chain.length - 1) {
					return isSelfSigned(x509cert);
				}
				X509Certificate issuer = (X509Certificate) chain[i + 1];
				x509cert.verify(issuer.getPublicKey());
			} catch (SignatureException | InvalidKeyException e) {
				return false;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return false;
	}

	private static boolean isSelfSigned(X509Certificate cert) {
		try {
			cert.verify(cert.getPublicKey());
			return true;
		} catch (SignatureException | InvalidKeyException e) {
			return false;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public KeyPair generateKeyPair() {
		try {
			java.security.KeyPairGenerator keyGen = java.security.KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Date[] generateDates(int months) {
		Date startDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.MONTH, months);
		Date endDate = calendar.getTime();
		return new Date[] { startDate, endDate };
	}

	public CertificateInfo generateCertificateInfo(SubjectData subjectData, String issuerAlias, String alias,
			String country, String organizationUnit, String organization, String email, boolean isCA,
			Template template) {
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
		return certificateInfoRepository.save(certInfo);
	}

	public void revoke(Long id) {
		CertificateInfo ci = certificateInfoRepository.findById(id).get();
		ci.setRevoked(true);
		certificateInfoRepository.save(ci);
	}

}
