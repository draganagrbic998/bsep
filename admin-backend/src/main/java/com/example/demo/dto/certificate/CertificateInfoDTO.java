package com.example.demo.dto.certificate;

import com.example.demo.model.CertificateInfo;
import com.example.demo.model.Template;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
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
	private boolean basicConstraints;
	private String keyUsage;
	private String extendedKeyUsage;
	private Date startDate;
	private Date endDate;
	private boolean revoked;
	private Date revocationDate;
	private String revocationReason;
	private long numIssued;
	private List<CertificateInfoDTO> issued;

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
		this.basicConstraints = certificate.isBasicConstraints();
		this.keyUsage = certificate.getKeyUsage();
		this.extendedKeyUsage = certificate.getExtendedKeyUsage();
		this.startDate = certificate.getStartDate();
		this.endDate = certificate.getEndDate();
		this.revoked = certificate.isRevoked();
		this.revocationDate = certificate.getRevocationDate();
		this.revocationReason = certificate.getRevocationReason();
	}
	
}
