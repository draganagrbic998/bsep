package com.example.demo.dto;

import java.util.List;

public class UserDTO {

	private List<String> authorities;

	public UserDTO() {
		super();
	}
	
	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	
}
