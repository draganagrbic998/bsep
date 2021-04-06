package com.example.demo.dto.certificate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatedCertificateDTO {
	
	private String issuerAlias;
	private String alias;
	private String organizationUnit;
	private String type;
	private String certificate;

}