package com.example.demo.dto.certificate;

import com.example.demo.model.CertificateInfo;
import com.example.demo.model.Extensions;
import com.example.demo.model.enums.Template;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CertificateInfoDTO {

	private long id;
	private String issuerAlias;
	private String alias;
	private String commonName;
	private String organization;
	private String organizationUnit;
	private String country;
	private String email;
	private Template template;
	private boolean revoked;
	private long numIssued;
	private List<CertificateInfoDTO> issued;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Extensions extensions;

	public CertificateInfoDTO(CertificateInfo certificate) {
		this.id = certificate.getId();
		this.issuerAlias = certificate.getIssuerAlias();
		this.alias = certificate.getAlias();
		this.commonName = certificate.getCommonName();
		this.organization = certificate.getOrganization();
		this.organizationUnit = certificate.getOrganizationUnit();
		this.country = certificate.getCountry();
		this.email = certificate.getEmail();
		this.template = certificate.getTemplate();
		this.revoked = certificate.isRevoked();
		this.extensions = certificate.getExtensions();
	}
	
}
