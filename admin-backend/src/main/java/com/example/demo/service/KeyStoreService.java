package com.example.demo.service;

import com.example.demo.keystore.KeyStoreReader;
import com.example.demo.keystore.KeyStoreWriter;
import com.example.demo.model.CertificateInfo;
import com.example.demo.model.IssuerData;
import com.example.demo.utils.Constants;
import com.example.demo.utils.PkiProperties;

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
		this.keyStoreWriter.loadKeyStore(this.pkiProperties.getKeystore(), this.pkiProperties.getKeystorePassword().toCharArray());
	}

	public void saveKeyStore() {
		this.keyStoreWriter.saveKeyStore(this.pkiProperties.getKeystore(), this.pkiProperties.getKeystorePassword().toCharArray());
	}

	public PrivateKey readPrivateKey(String alias) {
		return this.keyStoreReader.readPrivateKey(this.pkiProperties.getKeystore(), 
				this.pkiProperties.getKeystorePassword(), alias, this.pkiProperties.getKeystorePassword());
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

	public IssuerData readIssuer(String alias) {
		return this.keyStoreReader.readIssuerFromStore(this.pkiProperties.getKeystore(),
				alias, this.pkiProperties.getKeystorePassword().toCharArray(),
				this.pkiProperties.getKeystorePassword().toCharArray());
	}

	public String saveSeparateKeys(CertificateInfo issuer, CertificateInfo cert, PrivateKey privateKey, Certificate[] chain) {
		String filename = Constants.CERTIFICATES_FOLDER + issuer.getAlias() + "_" + cert.getAlias() + ".jks";
		this.keyStoreWriter.loadKeyStore(null, this.pkiProperties.getKeystorePassword().toCharArray());
		this.keyStoreWriter.write(cert.getAlias(), privateKey, this.pkiProperties.getKeystorePassword().toCharArray(), chain);
		this.keyStoreWriter.saveKeyStore(filename, this.pkiProperties.getKeystorePassword().toCharArray());
		return filename;
	}

	public void updateTrustStore(CertificateInfo issuer, CertificateInfo cert, X509Certificate certificate, String subjectFilename) {
		String issuerFilename =  Constants.CERTIFICATES_FOLDER + issuer.getIssuerAlias() + "_" + issuer.getAlias() + ".jks";
		this.keyStoreWriter.addToTruststore(issuer, cert, certificate, issuerFilename, subjectFilename, 
				this.pkiProperties.getRoot(), this.pkiProperties.getKeystorePassword());
	}

}
