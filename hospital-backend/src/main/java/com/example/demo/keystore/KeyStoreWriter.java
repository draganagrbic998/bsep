package com.example.demo.keystore;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

@Component
public class KeyStoreWriter {

	public void addToTruststore(String hospitalAlias, String deviceAlias, X509Certificate newCertificate,
			String hospitalFilename, String subjectFilename, String keyStorePassword) {
		X509Certificate hospitalCert = null;
		try {
			KeyStore hospitalTrustStore = KeyStore.getInstance("JKS", "SUN");
			hospitalTrustStore.load(new FileInputStream(hospitalFilename), keyStorePassword.toCharArray());
			hospitalTrustStore.setCertificateEntry(deviceAlias, newCertificate);
			hospitalTrustStore.store(new FileOutputStream(hospitalFilename), keyStorePassword.toCharArray());
			hospitalCert = (X509Certificate) hospitalTrustStore.getCertificate(hospitalAlias);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		try {
			KeyStore subjectTrustStore = KeyStore.getInstance("JKS", "SUN");
			subjectTrustStore.load(new FileInputStream(subjectFilename), keyStorePassword.toCharArray());
			subjectTrustStore.setCertificateEntry(hospitalAlias, hospitalCert);
			subjectTrustStore.store(new FileOutputStream(subjectFilename), keyStorePassword.toCharArray());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
