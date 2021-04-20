package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class AuthTokenDTO {

	private long id;
	private String token;
	private List<String> authorities;
	
	public AuthTokenDTO(User user, String token) {
		super();
		this.id = user.getId();
		this.token = token;
		this.authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
	}

}
