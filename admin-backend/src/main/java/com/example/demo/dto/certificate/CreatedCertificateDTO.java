package com.example.demo.dto.certificate;

import com.example.demo.model.CertificateType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatedCertificateDTO {
	private String issuerAlias;
	private String alias;
	private String organizationUnit;
	private CertificateType type;
	private String certificate;
}