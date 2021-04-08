package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@SuppressWarnings("serial")
public class Authority implements GrantedAuthority {

	public enum Auth {
		SUPER_ADMIN, ADMIN, DOCTOR
	}

	private Long id;
	private Auth name;

	public Authority(Auth name) {
		this.name = name;
	}
		
	@Override
	public String getAuthority() {
		return this.name.name();
	}
	
}
