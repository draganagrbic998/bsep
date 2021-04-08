package com.example.demo.service;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.CertificateInfo;
import com.example.demo.repository.CertificateInfoRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CertificateValidationService {

	private final CertificateInfoRepository certificateRepository;
	private final KeyStoreService keyStoreService;
	
	@Transactional(readOnly = true)
	public boolean isCertificateValid(long id) {
		try {
			return this.isCertificateValid(this.certificateRepository.findById(id).get().getAlias());
		} 
		catch (Exception e) {
			return false;
		}
	}

	@Transactional(readOnly = true)
	public boolean isCertificateValid(String alias) {
		Certificate[] chain = this.keyStoreService.readCertificateChain(alias);
		if (chain == null) {
			return false;
		}

		for (int i = 0; i < chain.length; i++) {
			X509Certificate certificate = (X509Certificate) chain[i];
			CertificateInfo cert = this.certificateRepository.findById(certificate.getSerialNumber().longValue()).orElse(null);

			if (cert == null) {
				return false;
			}

			if (cert.isRevoked()) {
				return false;
			}

			if (new Date().after(certificate.getNotAfter()) || new Date().before(certificate.getNotBefore())) {
				return false;
			}

			try {
				if (i == chain.length - 1) {
					return this.isSelfSigned(certificate);
				}
				X509Certificate issuer = (X509Certificate) chain[i + 1];
				certificate.verify(issuer.getPublicKey());
			} 
			catch (SignatureException | InvalidKeyException e) {
				return false;
			} 
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return false;
	}

	public boolean isSelfSigned(X509Certificate certificate) {
		try {
			certificate.verify(certificate.getPublicKey());
			return true;
		} 
		catch (SignatureException | InvalidKeyException e) {
			return false;
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
