package com.example.demo.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogSearchDTO {
	
	@NotNull
	private String status;
	
	@NotNull
	private String description;
	
	@NotNull
	private String userName;
	
	@NotNull
	private String computerName;
	
	@NotNull
	private String serviceName;

}
