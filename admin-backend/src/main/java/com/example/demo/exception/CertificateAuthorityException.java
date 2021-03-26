package com.example.demo.exception;

public class CertificateAuthorityException extends Exception {

    public CertificateAuthorityException() {
        super("The issuer isn't a certificate authority.");
    }
}
