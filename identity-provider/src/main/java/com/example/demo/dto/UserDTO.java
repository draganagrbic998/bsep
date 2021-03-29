package com.example.demo.dto;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import com.example.demo.model.User;

public class UserDTO {

	private String token;
	private List<String> authorities;

	public UserDTO() {
		super();
	}
	
	public UserDTO(User user, String token) {
		super();
		this.token = token;
		this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
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
