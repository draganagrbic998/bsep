package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreatedCertificateDTO {
	
	@NotBlank(message = "Issuer alias cannot be empty")
	private String issuerAlias;
	
	@NotBlank(message = "Alias cannot be empty")
	private String alias;
	
	@NotBlank(message = "Organization unit cannot be empty")
	private String organizationUnit;
	
	@NotBlank(message = "Type must not be empty")
	private String type;
	
	@NotBlank(message = "Certificate cannot be empty")
	private String certificate;

}