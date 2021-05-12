package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@NoArgsConstructor
public class AuthTokenDTO {

	private long id;
	private String token;
	private List<String> roles;
	private List<String> authorities;

	public AuthTokenDTO(User user, String token) {
		super();
		this.id = user.getId();
		this.token = token;
		this.roles = user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList());
		this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}
}
