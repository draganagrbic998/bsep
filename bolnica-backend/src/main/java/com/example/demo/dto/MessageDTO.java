package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

public class MessageDTO {

	@NotBlank
	private String text;

	public MessageDTO() {
		super();
	}

	public MessageDTO(String text) {
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
