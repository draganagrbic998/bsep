package com.example.demo.service;

import com.example.demo.config.PkiProperties;
import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.keystore.KeyStoreReader;
import com.example.demo.keystore.KeyStoreWriter;
import com.example.demo.model.CertificateInfo;
import com.example.demo.model.IssuerData;
import com.example.demo.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

@Service
public class KeyStoreService {

	private final KeyStoreReader keyStoreReader;
	private final KeyStoreWriter keyStoreWriter;
	private final PkiProperties pkiProperties;

	@Autowired
	public KeyStoreService(KeyStoreReader keyStoreReader, KeyStoreWriter keyStoreWriter, PkiProperties pkiProperties) {
		this.keyStoreReader = keyStoreReader;
		this.keyStoreWriter = keyStoreWriter;
		this.pkiProperties = pkiProperties;
	}

	public void loadKeyStore() {
		keyStoreWriter.loadKeyStore(pkiProperties.getKeystorePath() + pkiProperties.getKeystoreName(),
				pkiProperties.getKeystorePassword().toCharArray());
	}

	public void saveKeyStore() {
		keyStoreWriter.saveKeyStore(pkiProperties.getKeystorePath() + pkiProperties.getKeystoreName(),
				pkiProperties.getKeystorePassword().toCharArray());
	}

	public void savePrivateKey(String alias, Certificate[] certificate, PrivateKey privateKey) {
		keyStoreWriter.write(alias, privateKey, pkiProperties.getKeystorePassword().toCharArray(), certificate);
	}

	public Certificate readCertificate(String alias) {
		return keyStoreReader.readCertificate(pkiProperties.getKeystorePath() + pkiProperties.getKeystoreName(),
				pkiProperties.getKeystorePassword(), alias);
	}

	public Certificate[] readCertificateChain(String alias) {
		return keyStoreReader.readCertificateChain(pkiProperties.getKeystorePath() + pkiProperties.getKeystoreName(),
				pkiProperties.getKeystorePassword(), alias);
	}

	public IssuerData readIssuerFromStore(String alias) throws CertificateNotFoundException {
		return keyStoreReader.readIssuerFromStore(pkiProperties.getKeystorePath() + pkiProperties.getKeystoreName(),
				alias, pkiProperties.getKeystorePassword().toCharArray(),
				pkiProperties.getKeystorePassword().toCharArray());
	}

	public PrivateKey readPrivateKey(String alias) {
		return keyStoreReader.readPrivateKey(pkiProperties.getKeystorePath() + pkiProperties.getKeystoreName(),
				pkiProperties.getKeystorePassword(), alias, pkiProperties.getKeystorePassword());
	}

	public String saveSeparateKeys(CertificateInfo issuerInfo, CertificateInfo certInfo, PrivateKey privateKey,
			Certificate[] newCertificateChain) {
		String filename = pkiProperties.getKeystorePath() + Constants.GENERATED_CERT_FOLDER + issuerInfo.getAlias()
				+ "_" + certInfo.getAlias() + "_" + certInfo.getOrganizationUnit() + ".jks";
		keyStoreWriter.loadKeyStore(null, pkiProperties.getKeystorePassword().toCharArray());
		keyStoreWriter.write(certInfo.getOrganizationUnit(), privateKey,
				pkiProperties.getKeystorePassword().toCharArray(), newCertificateChain);
		keyStoreWriter.saveKeyStore(filename, pkiProperties.getKeystorePassword().toCharArray());
		return filename;
	}

	public void addToTruststore(CertificateInfo issuerInfo, CertificateInfo certInfo, X509Certificate newCertificate,
			String subjectFilename) {
		String issuerFilename = pkiProperties.getKeystorePath() + Constants.GENERATED_CERT_FOLDER
				+ issuerInfo.getIssuerAlias() + "_" + issuerInfo.getAlias() + "_" + issuerInfo.getOrganizationUnit()
				+ ".jks";
		keyStoreWriter.addToTruststore(issuerInfo, certInfo, newCertificate, issuerFilename, subjectFilename,
				pkiProperties.getKeystorePassword());
	}
}
