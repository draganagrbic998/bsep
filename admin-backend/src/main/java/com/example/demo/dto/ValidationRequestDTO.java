package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ValidationRequestDTO {

	private long serial;
	
	@NotBlank(message = "Path cannot be empty")
	private String path;

}