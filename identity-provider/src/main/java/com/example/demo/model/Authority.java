package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@Entity
@Table(name = "authority_table")
public class Authority implements GrantedAuthority {

	public enum Authorities {
		SUPER_ADMIN, ADMIN, DOCTOR
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(unique = true)
	@Enumerated(EnumType.STRING)
	private Authorities name;
	
	@Override
	public String getAuthority() {
		return this.name.name();
	}
	
}
