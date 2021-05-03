package com.example.demo.exception;

@SuppressWarnings("serial")
public class InvalidIssuerException extends RuntimeException {

	public InvalidIssuerException() {
		super("The issuer is not valid.");
	}
	
}