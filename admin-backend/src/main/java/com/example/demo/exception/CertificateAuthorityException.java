package com.example.demo.exception;

@SuppressWarnings("serial")
public class CertificateAuthorityException extends RuntimeException {

    public CertificateAuthorityException() {
        super("The issuer isn't a certificate authority.");
    }
}
