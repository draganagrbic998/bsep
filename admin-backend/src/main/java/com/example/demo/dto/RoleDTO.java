package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO {

	private long id;
	
	@NotBlank(message = "Name cannot be null")
	private String name;
		
}
