package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogMeasureDTO {
	
	@NotBlank(message = "Text can't be blank")
	private String text;

}
