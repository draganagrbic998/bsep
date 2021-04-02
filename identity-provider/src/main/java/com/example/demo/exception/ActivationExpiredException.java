package com.example.demo.exception;

public class ActivationExpiredException extends Exception {

    public ActivationExpiredException() {
        super("The activation link has expired");
    }

}
