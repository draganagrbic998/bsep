package com.example.demo.service;

import com.example.demo.config.PkiProperties;
import com.example.demo.keystore.KeyStoreReader;
import com.example.demo.keystore.KeyStoreWriter;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

@Service
@AllArgsConstructor
public class KeyStoreService {

	private final KeyStoreReader keyStoreReader;
	private final KeyStoreWriter keyStoreWriter;
	private final PkiProperties pkiProperties;

	public Certificate readCertificate(String path, String alias) {
		return keyStoreReader.readCertificate(path, this.pkiProperties.getKeystorePassword(), alias);
	}

	public void updateTruststore(String deviceAlias, String certificatePath) {
		X509Certificate certificate = (X509Certificate) this.keyStoreReader.readCertificate(certificatePath,
				this.pkiProperties.getKeystorePassword(), deviceAlias);
		this.keyStoreWriter.addToTruststore(this.pkiProperties.getKeyAlias(), deviceAlias, certificate,
				this.pkiProperties.getKeystore(), certificatePath, this.pkiProperties.getKeystorePassword());
	}

}
