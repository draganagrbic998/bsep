package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
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

}
