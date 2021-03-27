package com.example.demo.dto;

import java.util.Date;

import com.sun.istack.NotNull;

public class SearchDTO {

	@NotNull
	private String insuredNumber;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	private Date date;
	
	public SearchDTO() {
		super();
	}

	public String getInsuredNumber() {
		return insuredNumber;
	}

	public void setInsuredNumber(String insuredNumber) {
		this.insuredNumber = insuredNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
