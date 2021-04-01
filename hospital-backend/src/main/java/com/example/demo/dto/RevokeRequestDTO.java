package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class RevokeRequestDTO {

	private long serial;

	@NotBlank(message = "Path cannot be empty")
	private String path;
	
}