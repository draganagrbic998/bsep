package com.example.demo.model;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

	private List<String> roles;
	private List<String> authorities;

	@Override
	public List<Authority> getAuthorities() {
		return this.authorities.stream().map(Authority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
	
	public boolean isAdmin() {
		return this.roles.stream().anyMatch(role -> role.equals("ADMIN"));
	}
	
	public boolean isDoctor() {
		return this.roles.stream().anyMatch(role -> role.equals("DOCTOR"));
	}

}
