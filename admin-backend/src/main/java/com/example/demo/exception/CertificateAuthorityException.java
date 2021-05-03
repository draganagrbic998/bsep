package com.example.demo.exception;

@SuppressWarnings("serial")
public class CertificateAuthorityException extends RuntimeException {

	public CertificateAuthorityException() {
		super("The certificate is not a CA.");
	}
	
}
