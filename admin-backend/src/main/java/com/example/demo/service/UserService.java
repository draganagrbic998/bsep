package com.example.demo.service;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.dto.AuthTokenDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;

import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

	private static final String AUTH_API = "https://localhost:8083/auth";
	private static final String USERS_API = "https://localhost:8083/users";


	private final RestTemplate restTemplate;

	@Autowired
	public UserService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public User loadUserByUsername(String token) {
		return this.restTemplate.postForEntity(AUTH_API, new TokenDTO(token), User.class).getBody();
	}

	public AuthTokenDTO login(LoginDTO loginDTO) {
		return this.restTemplate.postForEntity(AUTH_API + "/login", loginDTO, AuthTokenDTO.class).getBody();
	}

	public UserDTO create(UserDTO userDTO) {
		return this.restTemplate.postForEntity(USERS_API, userDTO, UserDTO.class).getBody();
	}

	public UserDTO update(UserDTO userDTO) {
		HttpEntity<UserDTO> userDTOHttpEntity = new HttpEntity<>(userDTO);
		return this.restTemplate.exchange(
				USERS_API,
				HttpMethod.PUT,
				userDTOHttpEntity,
				UserDTO.class).getBody();

	}

	public void delete(long id) {
		this.restTemplate.delete(USERS_API + "/" + id);
	}

	public List<UserDTO> read() {
		ParameterizedTypeReference<List<UserDTO>> responseType = new ParameterizedTypeReference<>() {};
		return this.restTemplate.exchange(USERS_API, HttpMethod.GET, null, responseType).getBody();
	}
}
