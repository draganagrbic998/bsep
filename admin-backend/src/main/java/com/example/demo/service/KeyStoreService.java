package com.example.demo.service;

import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.keystore.KeyStoreReader;
import com.example.demo.keystore.KeyStoreWriter;
import com.example.demo.model.IssuerData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.cert.Certificate;

@Service
public class KeyStoreService {

	@Value("${PKI.keystore_path}")
	public String keystore_path;

	@Value("${PKI.keystore_name}")
	public String keystore_name;

	@Value("${PKI.keystore_password}")
	public String keystore_password;

	@Autowired
	private KeyStoreReader keyStoreReader;

	@Autowired
	private KeyStoreWriter keyStoreWriter;

	public void createKeyStore() {
		keyStoreWriter.createKeyStore(this.keystore_path, this.keystore_name, this.keystore_password.toCharArray());
	}

	public void loadKeyStore() {
		keyStoreWriter.loadKeyStore(this.keystore_path + this.keystore_name, this.keystore_password.toCharArray());
	}

	public void saveKeyStore() {
		keyStoreWriter.saveKeyStore(this.keystore_path + this.keystore_name, this.keystore_password.toCharArray());
	}
	
	public void savePrivateKey(String alias, Certificate[] certificate, PrivateKey privateKey) {
		keyStoreWriter.write(alias, privateKey, this.keystore_password.toCharArray(), certificate);
	}

	public Certificate readCertificate(String alias) {
		return keyStoreReader.readCertificate(this.keystore_path + this.keystore_name, this.keystore_password, alias);
	}

	public Certificate[] readCertificateChain(String alias) {
		return keyStoreReader.readCertificateChain(this.keystore_path + this.keystore_name, this.keystore_password,
				alias);
	}

	public IssuerData readIssuerFromStore(String alias) throws CertificateNotFoundException {
		return keyStoreReader.readIssuerFromStore(this.keystore_path + this.keystore_name, alias,
				this.keystore_password.toCharArray(), this.keystore_password.toCharArray());
	}

}
