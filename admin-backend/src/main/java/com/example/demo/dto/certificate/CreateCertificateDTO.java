package com.example.demo.dto.certificate;

import com.example.demo.model.Extensions;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.demo.model.Template;

@Data
@NoArgsConstructor
public class CreateCertificateDTO {

	long id;
	
	@NotBlank(message = "Issuer alias cannot be empty")
	private String issuerAlias;

	@NotBlank(message = "Alias cannot be empty")
	private String alias;

	@NotBlank(message = "Common name cannot be empty")
	private String commonName;

	@NotBlank(message = "Organization cannot be empty")
	private String organization;

	@NotBlank(message = "Organization unit cannot be empty")
	private String organizationUnit;

	@NotBlank(message = "Country cannot be empty")
	@Size(min = 2, max = 2, message = "Country must have two letters")
	private String country;

	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Email must be valid")
	private String email;

	@NotNull(message = "Template cannot be null")
	private Template template;
	
	private Extensions extensions;
	
}