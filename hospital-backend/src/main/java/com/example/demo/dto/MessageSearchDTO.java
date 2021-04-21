package com.example.demo.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageSearchDTO {

	@NotNull(message = "Insured number cannot be null")
	private String insuredNumber;
	
	@NotNull(message = "First name cannot be null")
	private String firstName;
	
	@NotNull(message = "Last name cannot be null")
	private String lastName;
	
	private Date date;
		
}
