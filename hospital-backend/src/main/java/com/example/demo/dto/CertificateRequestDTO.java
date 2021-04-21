package com.example.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.example.demo.model.CertificateType;
import com.example.demo.model.Template;

@Getter
@Setter
@NoArgsConstructor
public class CertificateRequestDTO {

	@NotBlank(message = "Alias cannot be empty")
	private String alias;

	@NotBlank(message = "Common name cannot be empty")
	private String commonName;

	@NotBlank(message = "Organization cannot be empty")
	private String organization;

	@NotBlank(message = "Organization unit cannot be empty")
	private String organizationUnit;

	@NotBlank(message = "Country cannot be empty")
	@Pattern(regexp = "[A-Z]{2}", message = "Country code must have two uppercase letters")
	private String country;

	@NotBlank(message = "Email cannot be empty")
	@Email(message = "Email must be valid")
	private String email;

	@NotNull(message = "Template cannot be null")
	private Template template;

	@NotNull(message = "Type cannot be null")
	private CertificateType type;
	
	private String path;
	
}