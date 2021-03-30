package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class Authority implements GrantedAuthority {

	public enum Authorities {
		SUPER_ADMIN, ADMIN, DOCTOR
	}

	private Authorities name;
		
	@Override
	public String getAuthority() {
		return this.name.name();
	}
	
}
