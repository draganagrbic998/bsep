package com.example.demo.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthTokenDTO {
	private long id;
	private String token;
	private List<RoleDTO> roles;
	private List<String> authorities;
}
