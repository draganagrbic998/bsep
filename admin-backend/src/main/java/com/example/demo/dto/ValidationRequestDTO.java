package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class ValidationRequestDTO {

	private long serial;
	private String path;

}