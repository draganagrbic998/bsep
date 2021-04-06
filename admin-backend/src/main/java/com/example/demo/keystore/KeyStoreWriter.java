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
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}

	public void saveKeyStore(String fileName, char[] password) {
		try {
			this.keyStore.store(new FileOutputStream(fileName), password);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void write(String alias, PrivateKey privateKey, char[] password, Certificate[] certificateChain) {
		try {
			this.keyStore.setKeyEntry(alias, privateKey, password, certificateChain);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addToTruststore(CertificateInfo issuerInfo, CertificateInfo certInfo,
			X509Certificate newCertificate, String issuerFilename, String subjectFilename, String keyStorePassword, String superadminFilename) {
		X509Certificate issuerCert = null;
		X509Certificate superadminCert = null;
		try {
			KeyStore issuerTrustStore = KeyStore.getInstance("JKS", "SUN");
			issuerTrustStore.load(new FileInputStream(issuerFilename), keyStorePassword.toCharArray());
			issuerTrustStore.setCertificateEntry(certInfo.getAlias(), newCertificate);
			issuerTrustStore.store(new FileOutputStream(issuerFilename), keyStorePassword.toCharArray());
			issuerCert = (X509Certificate) issuerTrustStore.getCertificate(issuerInfo.getAlias());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		//sacuvamo i u superadminski da bi mogo da komunicira s njim
		try {
			KeyStore superTrustStore = KeyStore.getInstance("JKS", "SUN");
			superTrustStore.load(new FileInputStream(superadminFilename), keyStorePassword.toCharArray());
			superTrustStore.setCertificateEntry(certInfo.getAlias(), newCertificate);
			superTrustStore.store(new FileOutputStream(superadminFilename), keyStorePassword.toCharArray());
			superadminCert = (X509Certificate) superTrustStore.getCertificate("super");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		try {
			KeyStore subjectTrustStore = KeyStore.getInstance("JKS", "SUN");
			subjectTrustStore.load(new FileInputStream(subjectFilename), keyStorePassword.toCharArray());
			subjectTrustStore.setCertificateEntry(issuerInfo.getAlias(), issuerCert);
			//sacuvamo i superadminski da bi mogo da komunicira s njim
			subjectTrustStore.setCertificateEntry("super", superadminCert);

			subjectTrustStore.store(new FileOutputStream(subjectFilename), keyStorePassword.toCharArray());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
