package com.example.demo.service;

import java.io.StringWriter;
import java.security.PrivateKey;
import java.security.cert.Certificate;

import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.util.io.pem.PemObject;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class KeyExportService {

	private final KeyStoreService keyStoreService;
	
	public String getCrt(String alias) {
		Certificate[] chain = this.keyStoreService.readCertificateChain(alias);
		StringBuilder chainBuilder = new StringBuilder();
		for (Certificate certificate: chain) {
			String pemCertificate = this.writePem(certificate);
			chainBuilder.append(pemCertificate);
		}
		return chainBuilder.toString();
	}

	public String getKey(String alias) {
		PrivateKey privateKey = this.keyStoreService.readPrivateKey(alias);
		PemObject pemFile = new PemObject("PRIVATE KEY", privateKey.getEncoded());
		return this.writePem(pemFile);
	}

	private String writePem(Object obj) {
		try {
			StringWriter writer = new StringWriter();
			JcaPEMWriter pemWriter = new JcaPEMWriter(writer);
			pemWriter.writeObject(obj);
			pemWriter.flush();
			pemWriter.close();
			return writer.toString();
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
