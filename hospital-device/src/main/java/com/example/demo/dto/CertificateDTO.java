package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CertificateDTO {

	private String issuerAlias;

	private String alias;

	private String organizationUnit;

	private String certificate;

}