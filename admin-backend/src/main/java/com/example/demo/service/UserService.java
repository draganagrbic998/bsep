package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import com.example.demo.utils.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public User loadUserByUsername(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constants.AUTH_HEADER, token);
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		return this.restTemplate.exchange(Constants.AUTH_API, HttpMethod.GET, entity, User.class).getBody();
	}

	public UserDTO login(LoginDTO loginDTO) {
		return this.restTemplate.postForEntity(Constants.AUTH_API + "/login", loginDTO, UserDTO.class).getBody();
	}
}
