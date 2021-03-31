package com.example.demo.dto;

import com.example.demo.model.Log;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogDTO {
	
	private String text;
	
	public LogDTO(Log log) {
		this.text = log.getText();
	}
	
}
