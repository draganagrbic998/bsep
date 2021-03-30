package com.example.demo.exception;

@SuppressWarnings("serial")
public class AliasExistsException extends Exception {

    public AliasExistsException() {
        super("Alias already exists");
    }

}
