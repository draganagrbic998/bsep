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

			if (keyStore.isKeyEntry(alias)) {
				Certificate cert = keyStore.getCertificateChain(alias)[0];
				in.close();
				return cert;
			}
			in.close();
		} 
		
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}