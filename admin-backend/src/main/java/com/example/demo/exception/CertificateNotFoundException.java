package com.example.demo.exception;

@SuppressWarnings("serial")
public class CertificateNotFoundException extends RuntimeException {

    public CertificateNotFoundException(String id) {
        super("Certificate: " + id + " not found");
    }

    public CertificateNotFoundException(String id, Throwable cause) {
        super("Certificate serial number: " + id + " not found", cause);
    }

}