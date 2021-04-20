package com.example.demo.exception;

public class AccountBlockedException extends Exception{
    public AccountBlockedException() {
        super("The account was disabled. Please contact an administrator in order to reactivate it.");
    }
}
