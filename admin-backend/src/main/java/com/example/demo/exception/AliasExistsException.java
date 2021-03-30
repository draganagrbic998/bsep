package com.example.demo.exception;

@SuppressWarnings("serial")
public class AliasExistsException extends RuntimeException {

    public AliasExistsException() {
        super("Alias already exists");
    }

}
