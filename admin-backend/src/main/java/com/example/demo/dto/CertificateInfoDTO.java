package com.example.demo.dto;

import com.example.demo.model.Template;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class CertificateInfoDTO {

	private long id;

	private Set<CertificateInfoDTO> issued;

	private long numIssued;

	private String alias;

	private String issuerAlias;

	private String commonName;

	private String serialNumber;

	private String organization;

	private String organizationUnit;

	private String country;

	private Date startDate;

	private Date endDate;

	private boolean revoked;

	private String revocationReason;

	private Date revocationDate;

	private boolean isCA;

	private String email;

	private Template template;
}
