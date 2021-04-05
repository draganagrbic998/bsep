package com.example.demo.exception;

@SuppressWarnings("serial")
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super(String.format("Email %s is already present in the system", email));
    }

}
