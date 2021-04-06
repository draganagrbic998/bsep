package com.example.demo.exception;

@SuppressWarnings("serial")
public class CertificateNotFoundException extends RuntimeException {

    public CertificateNotFoundException(String id) {
        super("Certificate: " + id + " not found");
    }

}