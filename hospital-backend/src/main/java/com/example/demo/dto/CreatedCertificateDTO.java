package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatedCertificateDTO {
	
	@NotBlank(message = "Issuer alias cannot be empty")
	private String issuerAlias;
	
	@NotBlank(message = "Alias cannot be empty")
	private String alias;
	
	@NotBlank(message = "Organization unit cannot be empty")
	private String organizationUnit;
	
	@NotBlank(message = "Certificate cannot be empty")
	private String certificate;

}