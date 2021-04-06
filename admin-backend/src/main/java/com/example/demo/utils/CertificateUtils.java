package com.example.demo.utils;

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

	public static X500Name certificateNameFromData(CreateCertificateDTO createCertificateDto) {
		X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
		builder.addRDN(BCStyle.CN, createCertificateDto.getCommonName());
		builder.addRDN(BCStyle.O, createCertificateDto.getOrganization());
		builder.addRDN(BCStyle.OU, createCertificateDto.getOrganizationUnit());
		builder.addRDN(BCStyle.E, createCertificateDto.getEmail());
		builder.addRDN(BCStyle.C, createCertificateDto.getCountry());
		return builder.build();
	}

	public static KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = java.security.KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
