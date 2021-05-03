package com.example.demo.exception;

@SuppressWarnings("serial")
public class AliasTakenException extends RuntimeException {

	public AliasTakenException() {
		super("The alias specified already exists.");
	}
	
}
