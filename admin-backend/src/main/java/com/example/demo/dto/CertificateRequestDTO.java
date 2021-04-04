package com.example.demo.dto;

import com.example.demo.model.CertificateRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CertificateRequestDTO {

	private long id;

	@NotBlank(message = "Alias cannot be empty")
	private String alias;

	@NotBlank(message = "Common name cannot be empty")
	private String commonName;

	@NotBlank(message = "Organization cannot be empty")
	private String organization;

	@NotBlank(message = "Organization unit cannot be empty")
	private String organizationUnit;

	@NotBlank(message = "Country cannot be empty")
	@Size(min = 2, max = 2)
	private String country;

	@NotBlank(message = "Email cannot be empty")
	@Email
	private String email;

	@NotBlank(message = "Template cannot be empty")
	private String template;

	private String path;
	
	public CertificateRequestDTO(CertificateRequest certificateRequest) {
		this.id = certificateRequest.getId();
		this.alias = certificateRequest.getAlias();
		this.commonName = certificateRequest.getCommonName();
		this.organization = certificateRequest.getOrganization();
		this.organizationUnit = certificateRequest.getOrganizationUnit();
		this.country = certificateRequest.getCountry();
		this.email = certificateRequest.getEmail();
		this.template = certificateRequest.getTemplate().toString();
		this.path = certificateRequest.getPath();
	}

}