package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService implements UserDetailsService {

	private static final String AUTH_API = "https://localhost:8083/auth";
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public User loadUserByUsername(String token) {
		return this.restTemplate.postForEntity(AUTH_API, new TokenDTO(token), User.class).getBody();
	}
	
	public UserDTO login(LoginDTO loginDTO) {
		return this.restTemplate.postForEntity(AUTH_API + "/login", loginDTO, UserDTO.class).getBody();
	}

}
