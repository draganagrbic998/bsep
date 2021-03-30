package com.example.demo.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogSearchDTO {
	
	@NotNull(message = "Status can't be null")
	private String status;
	
	@NotNull(message = "Description can't be null")
	private String description;
	
	@NotNull(message = "Username can't be null")
	private String userName;
	
	@NotNull(message = "Computer name can't be null")
	private String computerName;
	
	@NotNull(message = "Service name can't be null")
	private String serviceName;

}
