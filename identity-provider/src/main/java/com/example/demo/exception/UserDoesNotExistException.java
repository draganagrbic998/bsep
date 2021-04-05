package com.example.demo.exception;

@SuppressWarnings("serial")
public class UserDoesNotExistException extends RuntimeException {

    public UserDoesNotExistException() {
        super("The user specified doesn't exist");
    }

}
