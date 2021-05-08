package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {

	private long id;
	
	@NotBlank(message = "Name cannot be null")
	private String name;
		
}
