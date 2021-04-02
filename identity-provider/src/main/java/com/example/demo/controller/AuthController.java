package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.dto.*;
import com.example.demo.exception.ActivationExpiredException;
import com.example.demo.exception.UserDoesNotExistException;
import com.example.demo.model.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

	@Autowired
	private UserService userService;
		
	@Autowired
	private TokenUtils tokenUtils;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@PostMapping
	public ResponseEntity<AuthTokenDTO> auth(@Valid @RequestBody TokenDTO tokenDTO){
		try {
			String token = tokenDTO.getToken();
			User user = (User) this.userService.loadUserByUsername(this.tokenUtils.getEmail(token));
			if (user != null && this.tokenUtils.validateToken(user, token)) {
				return ResponseEntity.ok(new AuthTokenDTO(user, token));
			}
		}
		catch(Exception ignored) {
		}
		return ResponseEntity.ok(null);
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<AuthTokenDTO> login(@Valid @RequestBody LoginDTO loginDTO){
		try {
			User user = (User) this.authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())).getPrincipal();
			return ResponseEntity.ok(new AuthTokenDTO(user, this.tokenUtils.generateToken(user.getEmail())));
		}
		catch(Exception ignored) {
		}
		return ResponseEntity.ok(null);
	}

	@GetMapping(value = "/disabled/{uuid}")
	public ResponseEntity<UserDTO> getDisabled(@PathVariable String uuid) {
		return ResponseEntity.ok(this.userService.getDisabled(uuid));
	}

	@PostMapping(value = "activate")
	public ResponseEntity<UserDTO> activate(@RequestBody ActivationDTO activationDTO) throws ActivationExpiredException, UserDoesNotExistException {
		this.userService.activate(activationDTO);
		return ResponseEntity.ok().build();
	}
}
