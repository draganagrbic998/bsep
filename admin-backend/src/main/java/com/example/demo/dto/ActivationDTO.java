package com.example.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ActivationDTO {
	
	@NotBlank(message = "UUID cannot be empty")
    private String uuid;
	
	@NotNull(message = "Password cannot be empty")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$", message = "Password must be atleast 8 characters long and contain alteast one lowercase and uppercase letter and a number")
    private String password;
	
}
