package com.example.demo.service;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.dto.AuthTokenDTO;
import com.example.demo.model.User;
import com.example.demo.utils.AuthenticationProvider;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private static final String AUTH_API = Constants.IDENTITY_BACKEND + "/auth";
	
	private final RestTemplate restTemplate;
	private final AuthenticationProvider authProvider;

	@Override
	public User loadUserByUsername(String token) {
		return this.restTemplate.exchange(
				AUTH_API, 
				HttpMethod.POST, 
				this.authProvider.getAuthEntity(new TokenDTO(token)), 
				User.class).getBody();
	}
	
	public AuthTokenDTO login(LoginDTO loginDTO) {
		return this.restTemplate.exchange(
				AUTH_API + "/login", 
				HttpMethod.POST, 
				this.authProvider.getAuthEntity(loginDTO), 
				AuthTokenDTO.class).getBody();
	}
	
	public User currentUser() {
		try {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e) {
			return null;
		}
	}
	
}
