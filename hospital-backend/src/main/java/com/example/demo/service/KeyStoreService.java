package com.example.demo.service;

import com.example.demo.config.PkiProperties;
import com.example.demo.keystore.KeyStoreReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.Certificate;

@Service
public class KeyStoreService {

	private final KeyStoreReader keyStoreReader;
	private final PkiProperties pkiProperties;

	@Autowired
	public KeyStoreService(KeyStoreReader keyStoreReader, PkiProperties pkiProperties) {
		this.keyStoreReader = keyStoreReader;
		this.pkiProperties = pkiProperties;
	}

	public Certificate readCertificate(String path) {
		return keyStoreReader.readCertificate(path, "XSecret");
	}

	public Certificate[] readCertificateChain(String path) {
		return keyStoreReader.readCertificateChain(path, "XSecret");
	}
}
