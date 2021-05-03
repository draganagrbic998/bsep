package com.example.demo.exception;

@SuppressWarnings("serial")
public class InvalidCertificateException extends RuntimeException {

	public InvalidCertificateException() {
		super("The certificate is not valid.");
	}
	
}
