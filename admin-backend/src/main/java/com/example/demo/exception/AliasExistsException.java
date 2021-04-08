package com.example.demo.exception;

@SuppressWarnings("serial")
public class AliasExistsException extends RuntimeException {

	public AliasExistsException() {
		super("The alias specified already exists");
	}
	
}
