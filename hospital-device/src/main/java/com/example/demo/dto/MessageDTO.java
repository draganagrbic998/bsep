package com.example.demo.dto;

public class MessageDTO {

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
