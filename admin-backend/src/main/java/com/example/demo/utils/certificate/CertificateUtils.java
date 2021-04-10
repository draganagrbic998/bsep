package com.example.demo.utils.certificate;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

import com.example.demo.dto.certificate.CreateCertificateDTO;

public class CertificateUtils {

	public static Date[] generateDates(int months) {
		Date startDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.add(Calendar.MONTH, months);
		Date endDate = calendar.getTime();
		return new Date[] { startDate, endDate };
	}

	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator generator = java.security.KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			generator.initialize(2048, random);
			return generator.generateKeyPair();
		} 
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static X500Name certificateNameFromData(CreateCertificateDTO certificateDTO) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, certificateDTO.getCommonName());
		builder.addRDN(BCStyle.O, certificateDTO.getOrganization());
		builder.addRDN(BCStyle.OU, certificateDTO.getOrganizationUnit());
		builder.addRDN(BCStyle.C, certificateDTO.getCountry());
		builder.addRDN(BCStyle.E, certificateDTO.getEmail());
		return builder.build();
	}
	
}
