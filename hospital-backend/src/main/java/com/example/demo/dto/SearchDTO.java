package com.example.demo.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchDTO {

	@NotNull(message = "Insured number can't be null")
	private String insuredNumber;
	
	@NotNull(message = "First name can't be null")
	private String firstName;
	
	@NotNull(message = "Last name can't be null")
	private String lastName;
	private Date date;
		
}
