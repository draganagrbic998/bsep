package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleDTO {

	private long id;
	
	@NotBlank(message = "Name cannot be null")
	private String name;
		
}
