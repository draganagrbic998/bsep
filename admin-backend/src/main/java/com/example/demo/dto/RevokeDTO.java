package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class RevokeDTO {

	private long id;

	@NotBlank(message = "Reason cannot be empty")
	private String reason;
}