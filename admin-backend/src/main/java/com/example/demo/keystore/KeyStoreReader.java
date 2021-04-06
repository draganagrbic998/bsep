package com.example.demo.keystore;

import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.model.IssuerData;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

@Component
public class KeyStoreReader {

	private KeyStore keyStore;

	public KeyStoreReader() {
		try {
			this.keyStore = KeyStore.getInstance("JKS", "SUN");
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Certificate[] readCertificateChain(String storeName, String storePassword, String alias) {
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(storeName));
			keyStore.load(in, storePassword.toCharArray());

			if (keyStore.isKeyEntry(alias)) {
				return keyStore.getCertificateChain(alias);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Certificate readCertificate(String storeName, String storePassword, String alias) {
		return this.readCertificateChain(storeName, storePassword, alias)[0];
	}

	public PrivateKey readPrivateKey(String storeName, String storePassword, String alias, String password) {
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(storeName));
			keyStore.load(in, storePassword.toCharArray());

			if(keyStore.isKeyEntry(alias)) {
				return (PrivateKey) keyStore.getKey(alias, password.toCharArray());
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public IssuerData readIssuerFromStore(String storeName, String alias, char[] password, char[] storePassword) {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(storeName));
			this.keyStore.load(in, password);
			Certificate certificate = this.keyStore.getCertificate(alias);
			if (certificate == null)
				throw new CertificateNotFoundException(alias);
			
			PrivateKey privateKey = (PrivateKey) this.keyStore.getKey(alias, storePassword);
			X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) certificate).getSubject();
			return new IssuerData(privateKey, issuerName);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}