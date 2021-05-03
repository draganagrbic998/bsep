package com.example.demo.exception;

@SuppressWarnings("serial")
public class CertificateNotFoundException extends RuntimeException {

	public CertificateNotFoundException() {
		super("The certificate was not found.");
	}
	
}