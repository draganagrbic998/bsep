package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActivationDTO {
	
	@NotBlank(message = "UUID cannot be blank")
    private String uuid;
	
	@NotBlank(message = "Password cannot be blank")
    private String password;
}
