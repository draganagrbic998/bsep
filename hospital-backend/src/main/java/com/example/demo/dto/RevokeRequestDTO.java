package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RevokeRequestDTO {

	private long serial;

	@NotBlank(message = "Path cannot be empty")
	private String path;
	
}