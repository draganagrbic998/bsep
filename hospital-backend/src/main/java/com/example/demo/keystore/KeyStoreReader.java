package com.example.demo.keystore;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.Certificate;

import org.springframework.stereotype.Component;

@Component
public class KeyStoreReader {

	public Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			keyStore.load(in, keyStorePass.toCharArray());
			in.close();			
			return keyStore.isKeyEntry(alias) ? keyStore.getCertificateChain(alias)[0] : null;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}