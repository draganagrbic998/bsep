package com.example.demo.exception;

@SuppressWarnings("serial")
public class CommonlyUsedPasswordException extends RuntimeException {

    public CommonlyUsedPasswordException() {
        super("The chosen password is a commonly used one.");
    }

}

