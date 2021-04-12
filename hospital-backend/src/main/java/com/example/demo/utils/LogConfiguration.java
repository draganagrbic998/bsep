package com.example.demo.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class LogConfiguration {

	@NotBlank
	private String path;

	@Positive
	private long interval;

	@NotBlank
	private String regExp;
}
