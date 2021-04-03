package com.example.demo.keystore;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

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

}
