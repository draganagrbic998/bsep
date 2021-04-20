package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.User;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthController {

	private final UserService userService;
	private final TokenUtils tokenUtils;
	private final AuthenticationManager authManager;
	
	@PostMapping
	public ResponseEntity<AuthTokenDTO> auth(@Valid @RequestBody TokenDTO tokenDTO){
		try {
			String token = tokenDTO.getToken();
			User user = (User) this.userService.loadUserByUsername(this.tokenUtils.getEmail(token));
			if (user != null && this.tokenUtils.validateToken(user, token)) {
				return ResponseEntity.ok(new AuthTokenDTO(user, token));
			}
			throw new Exception();
		}
		catch(Exception e) {
			return ResponseEntity.ok(null);
		}
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<AuthTokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) throws AuthenticationException, BadCredentialsException {
		User user = (User) this.authManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())).getPrincipal();
		return ResponseEntity.ok(new AuthTokenDTO(user, this.tokenUtils.generateToken(user.getEmail())));
	}

	@PostMapping(value = "activate")
	public ResponseEntity<UserDTO> activate(@RequestBody ActivationDTO activationDTO) {
		return ResponseEntity.ok(new UserDTO(this.userService.activate(activationDTO)));
	}

	@GetMapping(value = "/disabled/{uuid}")
	public ResponseEntity<UserDTO> getDisabled(@PathVariable String uuid) {
		return ResponseEntity.ok(new UserDTO(this.userService.getDisabled(uuid)));
	}
	
}
