package com.example.demo.dto;

import javax.validation.constraints.NotNull;

import com.example.demo.model.Role;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO {

	private long id;
	
	@NotNull(message = "Name cannot be null")
	private Role.Roles name;
	
	public RoleDTO(Role role) {
		this.id = role.getId();
		this.name = role.getName();
	}
	
}
