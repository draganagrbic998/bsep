package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ValidationRequestDTO {

	@NotBlank(message = "Alias cannot be empty")
	private String alias;

	@NotBlank(message = "Path cannot be empty")
	private String path;
}