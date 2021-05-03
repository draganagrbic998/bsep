package com.example.demo.dto.certificate;

import com.example.demo.model.CertificateRequest;
import com.example.demo.model.enums.Template;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class CertificateRequestDTO {

	private Long id;

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

	@NotBlank(message = "Type cannot be empty")
	private String type;
	
	@NotBlank(message = "Path cannot be empty")
	private String path;

	public CertificateRequestDTO(CertificateRequest certificateRequest) {
		this.id = certificateRequest.getId();
		this.alias = certificateRequest.getAlias();
		this.commonName = certificateRequest.getCommonName();
		this.organization = certificateRequest.getOrganization();
		this.organizationUnit = certificateRequest.getOrganizationUnit();
		this.country = certificateRequest.getCountry();
		this.email = certificateRequest.getEmail();
		this.template = certificateRequest.getTemplate();
		this.type = certificateRequest.getType().name();
		this.path = certificateRequest.getPath();
	}

}