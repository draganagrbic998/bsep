package com.example.demo.service;

import com.example.demo.config.PkiProperties;
import com.example.demo.keystore.KeyStoreReader;
import com.example.demo.keystore.KeyStoreWriter;
import com.example.demo.utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

@Service
public class KeyStoreService {

	@Autowired
	private KeyStoreReader keyStoreReader;

	@Autowired
	private KeyStoreWriter keyStoreWriter;

	@Autowired
	private PkiProperties pkiProperties;

	public Certificate readCertificate(String path, String alias) {
		return keyStoreReader.readCertificate(path, pkiProperties.getKeystorePassword(), alias);
	}

	public void updateTruststore(String deviceAlias, String newCertificatePath) {
		X509Certificate newCertificate = (X509Certificate) keyStoreReader.readCertificate(newCertificatePath,
				pkiProperties.getKeystorePassword(), deviceAlias);
		this.keyStoreWriter.addToTruststore(pkiProperties.getKeyAlias(), deviceAlias, newCertificate,
				Constants.KEYSTORE_PATH, newCertificatePath, pkiProperties.getKeystorePassword());
	}

}