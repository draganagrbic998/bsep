package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

public class MessageMeasureDTO {

	@NotBlank
	private String text;

	public MessageMeasureDTO() {
		super();
	}

	public MessageMeasureDTO(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
