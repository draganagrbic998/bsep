package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class TokenDTO {
	
	@NotBlank(message = "Token cannot be blank")
	private String token;
	
}
