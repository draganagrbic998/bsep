package com.example.demo.exception;

public class UserDoesNotExistException extends Exception {

    public UserDoesNotExistException() {
        super("The user specified doesn't exist");
    }

}
