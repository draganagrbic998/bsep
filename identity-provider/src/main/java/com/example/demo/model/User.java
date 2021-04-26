package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name = "user_table")
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Email
	@NotBlank
	@Column(unique = true)
	private String email;
	
	private String password;
		
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;

	@NotNull
	private boolean enabled;

	@NotNull
	private Instant createdDate;
	
	private Instant activationExpiration;

	@Column(unique = true)
	private String activationLink;
		
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", 
    	joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
    	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

	public User() {
		super();
		this.createdDate = Instant.now();
		this.activationExpiration = Instant.now().plus(48, ChronoUnit.HOURS);
		this.activationLink = UUID.randomUUID().toString();
	}

	@Override
	public Set<Privilege> getAuthorities() {
		Set<Privilege> privileges = new HashSet<>();
		this.roles.forEach(role -> privileges.addAll(role.getPrivileges()));
		return privileges;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	
}
