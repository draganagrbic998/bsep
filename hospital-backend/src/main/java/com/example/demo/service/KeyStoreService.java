package com.example.demo.service;

import com.example.demo.keystore.KeyStoreReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.Certificate;

@Service
public class KeyStoreService {

	@Autowired
	private KeyStoreReader keyStoreReader;

	public Certificate readCertificate(String path) {
		return keyStoreReader.readCertificate(path, "XSecret");
	}
	
}
