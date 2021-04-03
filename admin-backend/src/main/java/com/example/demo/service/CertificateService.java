package com.example.demo.service;

import com.example.demo.dto.CertificateRequestDTO;
import com.example.demo.dto.CreateCertificateDTO;
import com.example.demo.dto.CreatedCertificateDTO;
import com.example.demo.exception.AliasExistsException;
import com.example.demo.exception.CertificateAuthorityException;
import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.exception.InvalidIssuerException;
import com.example.demo.mapper.CertificateRequestMapper;
import com.example.demo.model.*;
import com.example.demo.repository.CertificateInfoRepository;
import com.example.demo.repository.CertificateRequestRepository;
import com.example.demo.utils.CertificateGenerator;
import com.example.demo.utils.Constants;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.mail.MessagingException;

@Service
public class CertificateService {

	private final KeyStoreService keyStoreService;
	private final CertificateInfoRepository certificateInfoRepository;
	private final CertificateGenerator certificateGenerator;
	private final CertificateRequestRepository certificateRequestRepository;
	private final CertificateRequestMapper certificateRequestMapper;
	private final RestTemplate restTemplate;
	private final EmailService emailService;

	@Autowired
	public CertificateService(KeyStoreService keyStoreService, CertificateInfoRepository certificateInfoRepository,
			CertificateGenerator certificateGenerator, CertificateRequestRepository certificateRequestRepository,
			CertificateRequestMapper certificateRequestMapper, RestTemplate restTemplate, EmailService emailService) {
		this.keyStoreService = keyStoreService;
		this.certificateInfoRepository = certificateInfoRepository;
		this.certificateGenerator = certificateGenerator;
		this.certificateRequestRepository = certificateRequestRepository;
		this.certificateRequestMapper = certificateRequestMapper;
		this.restTemplate = restTemplate;
		this.emailService = emailService;
	}

	public void create(CreateCertificateDTO createCertificateDto) {
		this.keyStoreService.loadKeyStore();

		String issuerAlias = createCertificateDto.getIssuerAlias();

		Certificate[] issuerCertificateChain = this.keyStoreService.readCertificateChain(issuerAlias);
		IssuerData issuerData = this.keyStoreService.readIssuerFromStore(issuerAlias);

		X509Certificate issuer = (X509Certificate) issuerCertificateChain[0];
		CertificateInfo issuerInfo = this.certificateInfoRepository.findByAliasIgnoreCase(issuerAlias);
		if (!isCertificateValid(issuerAlias))
			throw new InvalidIssuerException();

		try {
			if (issuer.getBasicConstraints() == -1 || !issuer.getKeyUsage()[5]) {
				throw new CertificateAuthorityException();
			}
		} catch (NullPointerException ignored) {
		}

		String alias = createCertificateDto.getAlias();

		if (this.certificateInfoRepository.findByAliasIgnoreCase(alias) != null)
			throw new AliasExistsException();

		KeyPair keyPair = generateKeyPair();

		if (keyPair == null)
			return;

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

		this.keyStoreService.savePrivateKey(createCertificateDto.getAlias(), newCertificateChain, keyPair.getPrivate());
		this.keyStoreService.saveKeyStore();

		// cuvamo ga i u nov keystore da bi mogli posle da ga saljemo kome treba
		String filename = this.keyStoreService.saveSeparateKeys(issuerInfo, certInfo, keyPair.getPrivate(),
				newCertificateChain);

		// i u truststore
		this.keyStoreService.addToTruststore(issuerInfo, certInfo, createdCertificate, filename);

		// saljemo traziocu i brisemo zahtev ako je sertifikat napravljen po zahtevu
		byte[] returnValue = null;
		String fileName = issuerInfo.getAlias() + "_" + certInfo.getAlias() + "_" + certInfo.getOrganizationUnit();
		InputStream in = null;

		try {
			// ovo ako ne prodje znaci da ovi fajlovi ne postoje
			in = new FileInputStream("./src/main/resources/" + Constants.GENERATED_CERT_FOLDER + fileName + ".jks");
			returnValue = IOUtils.toByteArray(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			CreatedCertificateDTO dto = new CreatedCertificateDTO();
			dto.setIssuerAlias(issuerInfo.getAlias());
			dto.setAlias(certInfo.getAlias());
			dto.setOrganizationUnit(certInfo.getOrganizationUnit());
			dto.setCertificate(Base64.getEncoder().encodeToString(returnValue));

			if (createCertificateDto.getId() != 0) {
				this.restTemplate.postForEntity(
						this.certificateRequestRepository.findById(createCertificateDto.getId()).orElse(null).getPath(),
						dto, CreatedCertificateDTO.class).getBody();

				this.certificateRequestRepository.deleteById(createCertificateDto.getId());
			} else
				this.restTemplate
						.postForEntity("https://" + createCertificateDto.getPath() + Constants.CERTIFICATE_SAVE_PATH,
								dto, CreatedCertificateDTO.class)
						.getBody();

			String certFileName = certInfo.getIssuerAlias() + "_" + certInfo.getAlias() + "_"
					+ certInfo.getOrganizationUnit() + ".jks";
			this.emailService.sendInfoMail(certInfo.getEmail(), certFileName, certInfo.getOrganizationUnit(),
					Constants.CERTIFICATE_ISSUED, Constants.ISSUED_TEMPLATE);
		} catch (RestClientException | IllegalArgumentException | MessagingException e) {
			e.printStackTrace();
		}
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

	public boolean isCertificateValid(String alias) {
		Certificate[] chain = this.keyStoreService.readCertificateChain(alias);

		if (chain == null) {
			return false;
		}

		Date now = new Date();
		X509Certificate x509cert;
		for (int i = 0; i < chain.length; i++) {
			x509cert = (X509Certificate) chain[i];

			CertificateInfo certificateInfo = this.certificateInfoRepository
					.findById(x509cert.getSerialNumber().longValue()).orElse(null);

			if (certificateInfo == null) {
				return false;
			}

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
		} catch (Exception e) {
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
		return this.certificateInfoRepository.save(certInfo);
	}

	public void revoke(long id, String revokeReason) throws MessagingException {
		CertificateInfo ci = this.certificateInfoRepository.findById(id).orElse(null);
		if (ci == null) {
			throw new CertificateNotFoundException(String.valueOf(id));
		}
		ci.setRevoked(true);
		ci.setRevocationReason(revokeReason);
		ci.setRevocationDate(new Date());
		this.certificateInfoRepository.save(ci);
		String certFileName = ci.getIssuerAlias() + "_" + ci.getAlias() + "_" + ci.getOrganizationUnit() + ".jks";
		this.emailService.sendInfoMail(ci.getEmail(), certFileName, revokeReason,
				Constants.CERTIFICATE_REVOKED, Constants.REVOKED_TEMPLATE);
	}

	public void createRequest(CertificateRequestDTO certificateRequestDTO) {
		this.certificateRequestRepository.save(this.certificateRequestMapper.map(certificateRequestDTO));
	}

	public Page<CertificateRequest> findAllRequests(Pageable pageable) {
		return this.certificateRequestRepository.findAll(pageable);
	}

	public String getCrt(String alias) throws IOException {
		Certificate[] chain = keyStoreService.readCertificateChain(alias);

		StringBuilder chainBuilder = new StringBuilder();
		for (Certificate c : chain) {
			String pemCertificate = this.writePem(c);
			chainBuilder.append(pemCertificate);
		}
		return chainBuilder.toString();
	}

	public String getKey(String alias) throws IOException {
		PrivateKey privateKey = keyStoreService.readPrivateKey(alias);
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
