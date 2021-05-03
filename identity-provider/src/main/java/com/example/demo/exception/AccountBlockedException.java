package com.example.demo.exception;

@SuppressWarnings("serial")
public class AccountBlockedException extends RuntimeException {
	
    public AccountBlockedException() {
        super("The account has been disabled. Please contact an administrator in order to reactivate it.");
    }

}
