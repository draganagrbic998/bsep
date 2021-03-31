package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

	private final UserService userService;

	@Autowired
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginDTO loginDTO){
		return ResponseEntity.ok(this.userService.login(loginDTO));
	}
}
