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
	private List<RoleDTO> roles;
	private List<String> authorities;
}
