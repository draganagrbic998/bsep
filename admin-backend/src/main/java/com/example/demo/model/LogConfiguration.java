package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
public class LogConfiguration {

	@NotBlank(message = "Path cannot be empty")
	private String path;

	@Positive(message = "Interval must be positive integer")
	private long interval;

	@NotBlank(message = "Regular expression cannot be blank")
	private String regExp;
	
}
