package com.example.demo.exception;

@SuppressWarnings("serial")
public class AccountBlockedException extends RuntimeException {
	
    public AccountBlockedException() {
        super("The account was disabled. Please contact an administrator in order to reactivate it.");
    }

}
