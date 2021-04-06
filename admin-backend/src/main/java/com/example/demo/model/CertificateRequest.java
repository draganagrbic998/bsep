package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.example.demo.dto.certificate.CertificateRequestDTO;

@Data
@NoArgsConstructor
@Entity
public class CertificateRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String alias;

	@NotBlank
	private String commonName;

	@NotBlank
	private String organization;

	@NotBlank
	private String organizationUnit;

	@NotBlank
	private String country;

	@NotBlank
	@Email
	private String email;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Template template;

	@NotNull
	private CertificateType type;

	@NotBlank
	private String path;

	public CertificateRequest(CertificateRequestDTO requestDTO) {
		this.id = requestDTO.getId();
		this.alias = requestDTO.getAlias();
		this.commonName = requestDTO.getCommonName();
		this.organization = requestDTO.getOrganization();
		this.organizationUnit = requestDTO.getOrganizationUnit();
		this.country = requestDTO.getCountry();
		this.email = requestDTO.getEmail();
		this.template = Template.valueOf(requestDTO.getTemplate());
		this.type = CertificateType.valueOf(requestDTO.getType());
		this.path = requestDTO.getPath();
	}
}
