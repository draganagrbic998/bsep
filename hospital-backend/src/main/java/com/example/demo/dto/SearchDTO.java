package com.example.demo.dto;

import java.util.Date;

import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchDTO {

	@NotNull
	private String insuredNumber;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	private Date date;
		
}
