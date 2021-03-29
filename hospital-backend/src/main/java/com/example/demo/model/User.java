package com.example.demo.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.dto.Authority;
import com.example.demo.utils.Constants;

@SuppressWarnings("serial")
public class User implements UserDetails {

	private List<String> authorities;

	public User() {
		super();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
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
		return this.authorities.stream().anyMatch(x -> x.equals(Constants.ADMIN));
	}
	
	public boolean isDoctor() {
		return this.authorities.stream().anyMatch(x -> x.equals(Constants.DOCTOR));
	}
	
}
