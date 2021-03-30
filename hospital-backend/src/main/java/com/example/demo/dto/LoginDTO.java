package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
	
	@NotBlank(message = "Email can't be blank")
	private String email;
	
	@NotBlank(message = "Password can't be blank")
	private String password;
	
}
