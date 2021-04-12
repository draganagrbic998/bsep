package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.utils.AuthenticationProvider;

import lombok.AllArgsConstructor;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private static final String AUTH_API = "https://localhost:8083/auth";
	private static final String USERS_API = "https://localhost:8083/api/users";

	private final EmailService emailService;
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

	public PageDTO<UserDTO> findAll(Pageable pageable) {
		ParameterizedTypeReference<PageDTO<UserDTO>> responseType = new ParameterizedTypeReference<>() {};
		return this.restTemplate.exchange(
				String.format("%s?page=%d&size=%d", USERS_API, pageable.getPageNumber(), pageable.getPageSize()),
				HttpMethod.GET,
				this.authProvider.getAuthEntity(null),
				responseType).getBody();
	}

	public List<Authority> findAllAuthorities() {
		ParameterizedTypeReference<List<Authority>> responseType = new ParameterizedTypeReference<>() {};
		return this.restTemplate.exchange(
				USERS_API + "/authorities",
				HttpMethod.GET,
				this.authProvider.getAuthEntity(null),
				responseType).getBody();
	}

	public UserDTO create(UserDTO userDTO) {
		UserDTO user = this.restTemplate.exchange(
				USERS_API, 
				HttpMethod.POST, 
				this.authProvider.getAuthEntity(userDTO), 
				UserDTO.class).getBody();
		this.sendActivationMail(user);
		return user;
	}

	public UserDTO update(long id, UserDTO userDTO) {
		return this.restTemplate.exchange(
				USERS_API + "/" + id,
				HttpMethod.PUT,
				this.authProvider.getAuthEntity(userDTO), 
				UserDTO.class).getBody();
	}

	public void delete(long id) {
		this.restTemplate.exchange(
				USERS_API + "/" + id, 
				HttpMethod.DELETE, 
				this.authProvider.getAuthEntity(null), 
				Void.class);
	}

	public void sendActivationMail(long id) {
		UserDTO user = this.restTemplate.exchange(
				USERS_API + "/send/" + id, 
				HttpMethod.GET, 
				this.authProvider.getAuthEntity(null), 
				UserDTO.class).getBody();
		this.sendActivationMail(user);
	}

	public UserDTO getDisabled(String uuid) {
		return this.restTemplate.exchange(
				AUTH_API + "/disabled/" + uuid, 
				HttpMethod.GET, 
				this.authProvider.getAuthEntity(null), 
				UserDTO.class).getBody();
	}

	public UserDTO activate(ActivationDTO activationDTO) {
		return this.restTemplate.exchange(
				AUTH_API + "/activate", 
				HttpMethod.POST, 
				this.authProvider.getAuthEntity(activationDTO), 
				UserDTO.class).getBody();
	}

	private void sendActivationMail(UserDTO userDTO) {
		this.emailService.sendActivationLink(userDTO.getEmail(), userDTO.getFirstName(), userDTO.getActivationLink());
	}

}
