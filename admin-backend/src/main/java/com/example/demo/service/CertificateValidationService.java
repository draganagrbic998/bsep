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

	private final KeyStoreService keyStoreService;
	private final CertificateInfoRepository certificateRepository;
	
	@Transactional(readOnly = true)
	public boolean isCertificateValid(long serial) {
		try {
			return this.isCertificateValid(this.certificateRepository.getOne(serial).getAlias());
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

		Date now = new Date();
		X509Certificate x509cert;
		for (int i = 0; i < chain.length; i++) {
			x509cert = (X509Certificate) chain[i];
			CertificateInfo certificateInfo = this.certificateRepository.findById(x509cert.getSerialNumber().longValue()).orElse(null);

			if (certificateInfo == null) {
				return false;
			}

			if (certificateInfo.isRevoked()) {
				return false;
			}

			if (now.after(x509cert.getNotAfter()) || now.before(x509cert.getNotBefore())) {
				return false;
			}

			try {
				if (i == chain.length - 1) {
					return this.isSelfSigned(x509cert);
				}
				X509Certificate issuer = (X509Certificate) chain[i + 1];
				x509cert.verify(issuer.getPublicKey());
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
