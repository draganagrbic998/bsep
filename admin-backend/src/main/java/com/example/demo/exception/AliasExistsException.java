package com.example.demo.exception;

public class AliasExistsException extends Exception {

    public AliasExistsException() {
        super("Alias already exists");
    }

}
