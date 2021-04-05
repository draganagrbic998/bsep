package com.example.demo.exception;

@SuppressWarnings("serial")
public class ActivationExpiredException extends Exception {

    public ActivationExpiredException() {
        super("The activation link has expired");
    }

}
