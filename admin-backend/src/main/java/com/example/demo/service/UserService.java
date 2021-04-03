package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.Authority;
import com.example.demo.model.User;

import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

	private static final String AUTH_API = "https://localhost:8083/auth";
	private static final String USERS_API = "https://localhost:8083/api/users";


	private final RestTemplate restTemplate;
	private final EmailService emailService;

	@Autowired
	public UserService(RestTemplate restTemplate,
					   EmailService emailService) {
		this.restTemplate = restTemplate;
		this.emailService = emailService;
	}

	@Override
	public User loadUserByUsername(String token) {
		return this.restTemplate.postForEntity(AUTH_API, new TokenDTO(token), User.class).getBody();
	}

	public AuthTokenDTO login(LoginDTO loginDTO) {
		return this.restTemplate.postForEntity(AUTH_API + "/login", loginDTO, AuthTokenDTO.class).getBody();
	}

	public UserDTO create(UserDTO userDTO) throws MessagingException {
		UserDTO created = this.restTemplate.postForEntity(USERS_API, userDTO, UserDTO.class).getBody();
		if (created == null) return null;

		this.sendActivationMail(created);

		return created;
	}

	public void sendActivationMail(long id) throws MessagingException {
		UserDTO userDTO = this.restTemplate
				.getForEntity(String.format("%s/send/%d", USERS_API, id), UserDTO.class).getBody();

		if (userDTO == null) return;

		this.sendActivationMail(userDTO);
	}

	private void sendActivationMail(UserDTO userDTO) throws MessagingException {
		String link = userDTO.getActivationLink();
		String to = userDTO.getEmail();
		String firstName = userDTO.getFirstName();

		this.emailService.sendActivationLink(to, firstName, link);
	}


	public UserDTO getDisabled(String uuid) {
		return this.restTemplate.getForEntity(String.format("%s/disabled/%s", AUTH_API, uuid), UserDTO.class).getBody();
	}

	public UserDTO activate(ActivationDTO activationDTO) {
		return this.restTemplate.postForEntity(String.format("%s/activate", AUTH_API), activationDTO, UserDTO.class)
				.getBody();
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

	public PageDTO<UserDTO> read(Pageable pageable) {
		ParameterizedTypeReference<PageDTO<UserDTO>> responseType = new ParameterizedTypeReference<>() {};

		return this.restTemplate.exchange(
				String.format("%s?page=%d&size=%d", USERS_API, pageable.getPageNumber(), pageable.getPageSize()),
				HttpMethod.GET,
				new HttpEntity<>(null),
				responseType).getBody();
	}

	public List<Authority> getAuthorities() {
		ParameterizedTypeReference<List<Authority>> responseType = new ParameterizedTypeReference<>() {};
		return this.restTemplate.exchange(
				String.format("%s/authorities", USERS_API),
				HttpMethod.GET,
				new HttpEntity<>(null),
				responseType).getBody();
	}
}
