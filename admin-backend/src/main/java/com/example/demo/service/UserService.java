package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.User;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.utils.AuthenticationProvider;
import com.example.demo.utils.Logger;

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

	private final static String AUTH_API = "https://localhost:8083/auth";
	private final static String USERS_API = "https://localhost:8083/api/users";

	private final EmailService emailService;
	private final RestTemplate restTemplate;
	private final AuthenticationProvider authProvider;
	private final Logger logger;

	@Override
	public User loadUserByUsername(String token) {
		try {
			return this.restTemplate.exchange(
					AUTH_API, 
					HttpMethod.POST, 
					this.authProvider.getAuthEntity(new TokenDTO(token)), 
					User.class).getBody();
		} 
		catch (Exception e) {
			return null;
		}
	}

	public AuthTokenDTO login(LoginDTO loginDTO) {
		try {
			return this.restTemplate.exchange(
					AUTH_API + "/login", 
					HttpMethod.POST, 
					this.authProvider.getAuthEntity(loginDTO), 
					AuthTokenDTO.class).getBody();
		}
		catch(Exception e) {
			throw e;
		}
	}

	public PageDTO<UserDTO> findAll(Pageable pageable) {
		try {
			ParameterizedTypeReference<PageDTO<UserDTO>> responseType = new ParameterizedTypeReference<>() {};
			PageDTO<UserDTO> response = this.restTemplate.exchange(
					String.format("%s?page=%d&size=%d", USERS_API, pageable.getPageNumber(), pageable.getPageSize()),
					HttpMethod.GET,
					this.authProvider.getAuthEntity(null),
					responseType).getBody();
			this.logger.write(LogStatus.SUCCESS, String.format("Users page number %d successfully fetched.", pageable.getPageNumber()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching users page number %d.", pageable.getPageNumber()));
			throw e;
		}
	}

	public List<RoleDTO> findAllRoles() {
		try {
			ParameterizedTypeReference<List<RoleDTO>> responseType = new ParameterizedTypeReference<>() {};
			List<RoleDTO> response = this.restTemplate.exchange(
					USERS_API + "/roles",
					HttpMethod.GET,
					this.authProvider.getAuthEntity(null),
					responseType).getBody();
			this.logger.write(LogStatus.SUCCESS, "Roles successfully fetched.");
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while fetching roles.");
			throw e;
		}
	}

	public UserDTO create(UserDTO userDTO) {
		try {
			UserDTO user = this.restTemplate.exchange(
					USERS_API, 
					HttpMethod.POST, 
					this.authProvider.getAuthEntity(userDTO), 
					UserDTO.class).getBody();
			this.sendActivationMail(user);
			this.logger.write(LogStatus.SUCCESS, String.format("User with id %d successfully saved.", user.getId()));
			return user;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while saving user.");
			throw e;
		}
	}

	public UserDTO update(long id, UserDTO userDTO) {
		try {
			UserDTO response = this.restTemplate.exchange(
					USERS_API + "/" + id,
					HttpMethod.PUT,
					this.authProvider.getAuthEntity(userDTO), 
					UserDTO.class).getBody();
			this.logger.write(LogStatus.SUCCESS, String.format("User with id %d successfully saved.", response.getId()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while saving user.");
			throw e;
		}
	}

	public void delete(long id) {
		try {
			this.restTemplate.exchange(
					USERS_API + "/" + id, 
					HttpMethod.DELETE, 
					this.authProvider.getAuthEntity(null), 
					Void.class);
			this.logger.write(LogStatus.SUCCESS, String.format("User with id %d successfully deleted.", id));
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while deleting user with id %d.", id));
			throw e;
		}
	}

	public void sendActivationMail(long id) {
		try {
			UserDTO user = this.restTemplate.exchange(
					USERS_API + "/send/" + id, 
					HttpMethod.GET, 
					this.authProvider.getAuthEntity(null), 
					UserDTO.class).getBody();
			this.sendActivationMail(user);
			this.logger.write(LogStatus.SUCCESS, String.format("An activation mail has been successfully sent to user with id %d.", id));
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while sending activation main to user with id %d.", id));
			throw e;
		}
	}

	public UserDTO activate(ActivationDTO activationDTO) {
		try {
			UserDTO response = this.restTemplate.exchange(
					AUTH_API + "/activate", 
					HttpMethod.POST, 
					this.authProvider.getAuthEntity(activationDTO), 
					UserDTO.class).getBody();
			this.logger.write(LogStatus.SUCCESS, String.format("User with uuid %s successfully activated.", activationDTO.getUuid()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while activating user with uuid %s.", activationDTO.getUuid()));
			throw e;
		}
	}

	public UserDTO getDisabled(String uuid) {
		try {
			UserDTO response = this.restTemplate.exchange(
					AUTH_API + "/disabled/" + uuid, 
					HttpMethod.GET, 
					this.authProvider.getAuthEntity(null), 
					UserDTO.class).getBody();
			this.logger.write(LogStatus.SUCCESS, String.format("Disabled user with uuid %s successfully fetched.", uuid));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching disabled user with uuid %s.", uuid));
			throw e;
		}
	}

	private void sendActivationMail(UserDTO userDTO) {
		this.emailService.sendActivationLink(userDTO.getEmail(), userDTO.getFirstName(), userDTO.getActivationLink());
	}

}
