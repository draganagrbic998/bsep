package com.example.demo.keystore;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Iterator;

import org.springframework.stereotype.Component;

@Component
public class KeyStoreReader {

	private KeyStore keyStore;

	public KeyStoreReader() {
		try {
			this.keyStore = KeyStore.getInstance("JKS", "SUN");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Certificate[] readCertificateChain(String keyStoreFile, String keyStorePass) {
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());

			String alias = ks.aliases().nextElement();

			if (ks.isKeyEntry(alias)) {
				return ks.getCertificateChain(alias);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Certificate readCertificate(String keyStoreFile, String keyStorePass) {
		return this.readCertificateChain(keyStoreFile, keyStorePass)[0];
	}

}