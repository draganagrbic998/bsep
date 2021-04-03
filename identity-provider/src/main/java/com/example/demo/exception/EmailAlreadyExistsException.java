package com.example.demo.exception;

public class EmailAlreadyExistsException extends Exception {

    public EmailAlreadyExistsException(String email) {
        super(String.format("Email %s is already present in the system", email));
    }

}
