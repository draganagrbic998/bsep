package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivationDTO {
	
	@NotBlank(message = "UUID cannot be empty")
    private String uuid;
	
	@NotBlank(message = "Password cannot be empty")
    private String password;
}
