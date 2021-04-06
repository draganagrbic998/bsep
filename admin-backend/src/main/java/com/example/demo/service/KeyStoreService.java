package com.example.demo.service;

import com.example.demo.config.PkiProperties;
import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.keystore.KeyStoreReader;
import com.example.demo.keystore.KeyStoreWriter;
import com.example.demo.model.CertificateInfo;
import com.example.demo.model.IssuerData;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

@Service
@AllArgsConstructor
public class KeyStoreService {

	private final KeyStoreReader keyStoreReader;
	private final KeyStoreWriter keyStoreWriter;
	private final PkiProperties pkiProperties;

	public void loadKeyStore() {
		this.keyStoreWriter.loadKeyStore(this.pkiProperties.getKeystore(),
				this.pkiProperties.getKeystorePassword().toCharArray());
	}

	public void saveKeyStore() {
		this.keyStoreWriter.saveKeyStore(this.pkiProperties.getKeystore(), this.pkiProperties.getKeystorePassword().toCharArray());
	}

	public void savePrivateKey(String alias, Certificate[] certificate, PrivateKey privateKey) {
		this.keyStoreWriter.write(alias, privateKey, this.pkiProperties.getKeystorePassword().toCharArray(), certificate);
	}

	public Certificate readCertificate(String alias) {
		return this.keyStoreReader.readCertificate(this.pkiProperties.getKeystore(), this.pkiProperties.getKeystorePassword(), alias);
	}

	public Certificate[] readCertificateChain(String alias) {
		return this.keyStoreReader.readCertificateChain(this.pkiProperties.getKeystore(), this.pkiProperties.getKeystorePassword(), alias);
	}

	public IssuerData readIssuerFromStore(String alias) throws CertificateNotFoundException {
		return this.keyStoreReader.readIssuerFromStore(this.pkiProperties.getKeystore(),
				alias, this.pkiProperties.getKeystorePassword().toCharArray(),
				this.pkiProperties.getKeystorePassword().toCharArray());
	}

	public PrivateKey readPrivateKey(String alias) {
		return this.keyStoreReader.readPrivateKey(this.pkiProperties.getKeystore(),
				this.pkiProperties.getKeystorePassword(), alias, this.pkiProperties.getKeystorePassword());
	}

	public String saveSeparateKeys(CertificateInfo issuerInfo, CertificateInfo certInfo, PrivateKey privateKey,
			Certificate[] newCertificateChain) {
		String filename = Constants.RESOURCE_FOLDER + Constants.CERTIFICATES_FOLDER + issuerInfo.getAlias()
				+ "_" + certInfo.getAlias() + "_" + certInfo.getOrganizationUnit() + ".jks";
		this.keyStoreWriter.loadKeyStore(null, this.pkiProperties.getKeystorePassword().toCharArray());
		this.keyStoreWriter.write(certInfo.getAlias(), privateKey,
				this.pkiProperties.getKeystorePassword().toCharArray(), newCertificateChain);
		this.keyStoreWriter.saveKeyStore(filename, this.pkiProperties.getKeystorePassword().toCharArray());
		return filename;
	}

	public void addToTruststore(CertificateInfo issuerInfo, CertificateInfo certInfo, X509Certificate newCertificate,
			String subjectFilename) {
		String issuerFilename =  Constants.RESOURCE_FOLDER + Constants.CERTIFICATES_FOLDER
				+ issuerInfo.getIssuerAlias() + "_" + issuerInfo.getAlias() + "_" + issuerInfo.getOrganizationUnit()
				+ ".jks";
		this.keyStoreWriter.addToTruststore(issuerInfo, certInfo, newCertificate, issuerFilename, subjectFilename,
				this.pkiProperties.getKeystorePassword(), this.pkiProperties.getSuperadminJks());
	}

}
