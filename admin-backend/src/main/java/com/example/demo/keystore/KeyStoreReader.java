package com.example.demo.keystore;

import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.model.IssuerData;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Component;

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

	public IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass) {
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			this.keyStore.load(in, password);
			Certificate cert = this.keyStore.getCertificate(alias);
			if (cert == null)
				throw new CertificateNotFoundException(alias);
			PrivateKey privKey = (PrivateKey) this.keyStore.getKey(alias, keyPass);

			X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
			return new IssuerData(privKey, issuerName);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Certificate[] readCertificateChain(String keyStoreFile, String keyStorePass, String alias) {
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());

			if (ks.isKeyEntry(alias)) {
				return ks.getCertificateChain(alias);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
		return this.readCertificateChain(keyStoreFile, keyStorePass, alias)[0];
	}

	/**
	 * Ucitava privatni kljuc is KS fajla
	 */
	public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());

			if(ks.isKeyEntry(alias)) {
				return (PrivateKey) ks.getKey(alias, pass.toCharArray());
			}
		} catch (UnrecoverableKeyException |
				KeyStoreException |
				NoSuchProviderException |
				NoSuchAlgorithmException |
				CertificateException |
				IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}