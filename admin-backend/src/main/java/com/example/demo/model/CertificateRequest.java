package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.example.demo.dto.CertificateRequestDTO;

@Data
@NoArgsConstructor
@Entity
@Table(name = "certificate_request_table")
public class CertificateRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String alias;

	private String issuerAlias;

	private String commonName;

	private String organization;

	private String organizationUnit;

	private String country;

	private String email;

	@Enumerated(EnumType.STRING)
	private Template template;

	@Enumerated(EnumType.STRING)
	private CertificateType type;

	public CertificateRequest(CertificateRequestDTO certificateRequestDTO) {
		this.alias = certificateRequestDTO.getAlias();
		this.issuerAlias = certificateRequestDTO.getIssuerAlias();
		this.organization = certificateRequestDTO.getOrganization();
		this.organizationUnit = certificateRequestDTO.getOrganizationUnit();
		this.commonName = certificateRequestDTO.getCommonName();
		this.country = certificateRequestDTO.getCountry();
		this.email = certificateRequestDTO.getEmail();
	}
}