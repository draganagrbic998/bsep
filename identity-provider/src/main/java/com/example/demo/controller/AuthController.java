package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.dto.AuthTokenDTO;
import com.example.demo.model.User;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.UserService;

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
}
