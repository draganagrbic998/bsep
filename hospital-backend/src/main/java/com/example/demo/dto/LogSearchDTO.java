package com.example.demo.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogSearchDTO {
	
	@NotNull(message = "Mode cannot be null")
	private String mode;

	@NotNull(message = "Status cannot be null")
	private String status;
	
	@NotNull(message = "IP address cannot be null")
	private String ipAddress;

	@NotNull(message = "Description cannot be null")
	private String description;
	
	private Date date;
	
}
