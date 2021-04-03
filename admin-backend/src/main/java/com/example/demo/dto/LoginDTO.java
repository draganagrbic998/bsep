package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginDTO {
	
	@NotBlank
    private String email;
	
	@NotBlank
    private String password;
	
}
