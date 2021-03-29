package com.example.demo.dto;

import java.util.List;

public class UserDTO {

	private String token;
	private List<String> authorities;

	public UserDTO() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}
	
}
