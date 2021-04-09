package com.example.demo.controller;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("permitAll()")
@AllArgsConstructor
public class AuthController {

	private final UserService userService;
		
	@PostMapping(value = "/login")
	public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginDTO loginDTO){
		return ResponseEntity.ok(this.userService.login(loginDTO));
	}
	
}
