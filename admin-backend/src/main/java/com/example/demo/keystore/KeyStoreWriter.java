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
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void loadKeyStore(String fileName, char[] password) {
		try {
			if (fileName != null) {
				this.keyStore.load(new FileInputStream(fileName), password);
			} 
			else {
				this.keyStore.load(null, password);
			}
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void saveKeyStore(String fileName, char[] password) {
		try {
			this.keyStore.store(new FileOutputStream(fileName), password);
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void write(String alias, PrivateKey privateKey, char[] password, Certificate[] chain) {
		try {
			this.keyStore.setKeyEntry(alias, privateKey, password, chain);
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void addToTruststore(CertificateInfo issuer, CertificateInfo cert, X509Certificate certificate, 
			String issuerFilename, String subjectFilename, String superFilename, String password) {
		
		X509Certificate issuerCertificate = null;
		X509Certificate superadminCertificate = null;
		
		try {
			KeyStore issuerTrustStore = KeyStore.getInstance("JKS", "SUN");
			issuerTrustStore.load(new FileInputStream(issuerFilename), password.toCharArray());
			issuerTrustStore.setCertificateEntry(cert.getAlias(), certificate);
			issuerTrustStore.store(new FileOutputStream(issuerFilename), password.toCharArray());
			issuerCertificate = (X509Certificate) issuerTrustStore.getCertificate(issuer.getAlias());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			KeyStore superTrustStore = KeyStore.getInstance("JKS", "SUN");
			superTrustStore.load(new FileInputStream(superFilename), password.toCharArray());
			superTrustStore.setCertificateEntry(cert.getAlias(), certificate);
			superTrustStore.store(new FileOutputStream(superFilename), password.toCharArray());
			superadminCertificate = (X509Certificate) superTrustStore.getCertificate("super");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		try {
			KeyStore subjectTrustStore = KeyStore.getInstance("JKS", "SUN");
			subjectTrustStore.load(new FileInputStream(subjectFilename), password.toCharArray());
			subjectTrustStore.setCertificateEntry(issuer.getAlias(), issuerCertificate);
			subjectTrustStore.setCertificateEntry("super", superadminCertificate);
			subjectTrustStore.store(new FileOutputStream(subjectFilename), password.toCharArray());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	}
}
