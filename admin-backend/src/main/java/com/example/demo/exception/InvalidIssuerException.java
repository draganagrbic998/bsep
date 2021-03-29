package com.example.demo.exception;

public class InvalidIssuerException extends Exception {

    public InvalidIssuerException() {
        super("Issuer is not valid");
    }
}