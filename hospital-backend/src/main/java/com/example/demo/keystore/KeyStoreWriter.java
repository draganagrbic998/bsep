package com.example.demo.keystore;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

@Component
public class KeyStoreWriter {

	public void addToTruststore(String hospitalAlias, String deviceAlias, X509Certificate certificate,
			String hospitalFilename, String subjectFilename, String keyStorePassword) {
		
		X509Certificate hospitalCertificate = null;
		
		try {
			KeyStore hospitalTrustStore = KeyStore.getInstance("JKS", "SUN");
			hospitalTrustStore.load(new FileInputStream(hospitalFilename), keyStorePassword.toCharArray());
			hospitalTrustStore.setCertificateEntry(deviceAlias, certificate);
			hospitalTrustStore.store(new FileOutputStream(hospitalFilename), keyStorePassword.toCharArray());
			hospitalCertificate = (X509Certificate) hospitalTrustStore.getCertificate(hospitalAlias);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		try {
			KeyStore subjectTrustStore = KeyStore.getInstance("JKS", "SUN");
			subjectTrustStore.load(new FileInputStream(subjectFilename), keyStorePassword.toCharArray());
			subjectTrustStore.setCertificateEntry(hospitalAlias, hospitalCertificate);
			subjectTrustStore.store(new FileOutputStream(subjectFilename), keyStorePassword.toCharArray());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
