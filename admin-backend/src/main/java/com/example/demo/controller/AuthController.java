package com.example.demo.controller;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.TokenResponse;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import com.example.demo.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	@Autowired
	private AdminService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AdminMapper adminMapper;

	@PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenResponse> login(@RequestBody LoginDTO loginInfo) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginInfo.getUsername(), loginInfo.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			Admin user = (Admin) authentication.getPrincipal();
			String jwt = TokenUtils.generateToken(user);

			return ResponseEntity.ok(new TokenResponse(jwt, adminMapper.entityToAdminDto(user)));
		} catch (Exception ex) {
			return ResponseEntity.badRequest().build();
		}
	}
}
