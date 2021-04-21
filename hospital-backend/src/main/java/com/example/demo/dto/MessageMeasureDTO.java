package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageMeasureDTO {

	@NotBlank(message = "Text cannot be blank")
	private String text;
	
}
