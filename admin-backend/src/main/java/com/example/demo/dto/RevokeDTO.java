package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class RevokeDTO {

	private long id;

	@NotBlank(message = "Reason cannot be empty")
	private String reason;
}