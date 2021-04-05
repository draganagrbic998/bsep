package com.example.demo.service;

import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.demo.model.CertificateInfo;
import com.example.demo.repository.CertificateInfoRepository;
import com.example.demo.utils.CertificateUtils;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class CertificateValidationService {

	private final KeyStoreService keyStoreService;
	private final CertificateInfoRepository certificateInfoRepository;
	
	public boolean isCertificateValid(long serial) {
		String alias = null;
		try {
			alias = this.certificateInfoRepository.getOne(serial).getAlias();
		} 
		catch (Exception e) {
			return false;
		}
		return isCertificateValid(alias);
	}

	public boolean isCertificateValid(String alias) {
		Certificate[] chain = this.keyStoreService.readCertificateChain(alias);

		if (chain == null) {
			return false;
		}

		Date now = new Date();
		X509Certificate x509cert;
		for (int i = 0; i < chain.length; i++) {
			x509cert = (X509Certificate) chain[i];

			CertificateInfo certificateInfo = this.certificateInfoRepository.findById(x509cert.getSerialNumber().longValue()).orElse(null);

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
					return CertificateUtils.isSelfSigned(x509cert);
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

	
}
