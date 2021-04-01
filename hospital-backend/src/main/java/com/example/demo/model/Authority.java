package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority {

	public enum Authorities {
		ADMIN, DOCTOR;
	}

	private Authorities name;
		
	@Override
	public String getAuthority() {
		return this.name.name();
	}
	
}
