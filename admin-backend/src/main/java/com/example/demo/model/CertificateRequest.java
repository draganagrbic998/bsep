package com.example.demo.model;

import com.example.demo.dto.CertificateRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "certificate_request_table")
public class CertificateRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String alias;

	private String commonName;

	private String organization;

	private String organizationUnit;

	private String country;

	private String email;

	@Enumerated(EnumType.STRING)
	private Template template;

	private CertificateType type;

	private String path;

	public CertificateRequest(CertificateRequestDTO certificateRequestDTO) {
		this.alias = certificateRequestDTO.getAlias();
		this.organization = certificateRequestDTO.getOrganization();
		this.organizationUnit = certificateRequestDTO.getOrganizationUnit();
		this.commonName = certificateRequestDTO.getCommonName();
		this.country = certificateRequestDTO.getCountry();
		this.email = certificateRequestDTO.getEmail();
		this.template = Template.valueOf(certificateRequestDTO.getTemplate());
		this.type = CertificateType.valueOf(certificateRequestDTO.getType());
		this.path = certificateRequestDTO.getPath();
	}
}
