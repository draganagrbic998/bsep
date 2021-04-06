package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivationDTO {
	
	@NotBlank(message = "UUID can't be blank")
    private String uuid;
	
	@NotBlank(message = "Password can't be blank")
    private String password;
}
