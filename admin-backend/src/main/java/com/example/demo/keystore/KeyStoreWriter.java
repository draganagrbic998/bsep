package com.example.demo.keystore;

import org.springframework.stereotype.Component;

import com.example.demo.model.CertificateInfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

@Component
public class KeyStoreWriter {

	private KeyStore keyStore;

	public KeyStoreWriter() {
		try {
			this.keyStore = KeyStore.getInstance("JKS", "SUN");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadKeyStore(String fileName, char[] password) {
		try {
			if (fileName != null) {
				this.keyStore.load(new FileInputStream(fileName), password);
			} else {
				this.keyStore.load(null, password);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveKeyStore(String fileName, char[] password) {
		try {
			this.keyStore.store(new FileOutputStream(fileName), password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(String alias, PrivateKey privateKey, char[] password, Certificate[] certificateChain) {
		try {
			this.keyStore.setKeyEntry(alias, privateKey, password, certificateChain);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addToTruststore(CertificateInfo issuerInfo, CertificateInfo certInfo,
			X509Certificate newCertificate, String issuerFilename, String subjectFilename, String keyStorePassword) {
		X509Certificate issuerCert = null;
		X509Certificate rootCert = null;
		try {
			KeyStore issuerTrustStore = KeyStore.getInstance("JKS", "SUN");
			issuerTrustStore.load(new FileInputStream(issuerFilename), keyStorePassword.toCharArray());
			issuerTrustStore.setCertificateEntry(certInfo.getAlias(), newCertificate);
			issuerTrustStore.store(new FileOutputStream(issuerFilename), keyStorePassword.toCharArray());
			issuerCert = (X509Certificate) issuerTrustStore.getCertificate(issuerInfo.getAlias());
			
			//posto cuvamo ceo chain u svaki keystore, onda issuer mora da ima ili da jeste root
			rootCert = (X509Certificate) issuerTrustStore.getCertificate("root");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		try {
			KeyStore subjectTrustStore = KeyStore.getInstance("JKS", "SUN");
			subjectTrustStore.load(new FileInputStream(subjectFilename), keyStorePassword.toCharArray());
			
			//ako nam issuer nije root, dodajmo i root da bi mogli da komuniciraju
			if (rootCert.getSerialNumber() != issuerCert.getSerialNumber())
				subjectTrustStore.setCertificateEntry("root", rootCert);

			subjectTrustStore.setCertificateEntry(issuerInfo.getAlias(), issuerCert);
			subjectTrustStore.store(new FileOutputStream(subjectFilename), keyStorePassword.toCharArray());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
