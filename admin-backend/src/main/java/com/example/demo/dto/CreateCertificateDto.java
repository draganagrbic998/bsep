package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateCertificateDto {

    @NotBlank(message = "Issuer alias cannot be empty")
    private String issuerAlias;

    @NotBlank(message = "Alias cannot be empty")
    private String alias;

    @NotBlank(message = "Common name cannot be empty")
    private String commonName;

    @NotBlank(message = "Organization cannot be empty")
    private String organization;

    @NotBlank(message = "Organization unit cannot be empty")
    private String organizationUnit;

    @NotBlank(message = "Country cannot be empty")
    @Size(min=2, max=2)
    private String country;

    @NotBlank(message = "Email cannot be empty")
    @Email
    private String email;

    @NotBlank(message = "Template cannot be empty")
    private String template;

}