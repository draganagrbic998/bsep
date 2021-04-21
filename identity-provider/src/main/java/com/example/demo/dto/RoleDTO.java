package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import com.example.demo.model.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {

	private long id;
	
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	public RoleDTO(Role role) {
		this.id = role.getId();
		this.name = role.getName();
	}
	
}
