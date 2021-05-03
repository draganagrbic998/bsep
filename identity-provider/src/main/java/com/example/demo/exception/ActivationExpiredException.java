package com.example.demo.exception;

@SuppressWarnings("serial")
public class ActivationExpiredException extends RuntimeException {

    public ActivationExpiredException() {
        super("The activation link has expired.");
    }

}
