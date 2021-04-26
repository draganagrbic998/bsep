package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CertificateInfo {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String alias;

    @NotBlank
    private String commonName;

    @NotBlank
    private String organization;

    @NotBlank
    private String organizationUnit;

    @NotBlank
    @Pattern(regexp = "[A-Z]{2}")
    private String country;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Template template;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private boolean revoked;

    private Date revocationDate;

    private String revocationReason;
    
    @ManyToOne
    @JoinColumn(name = "issuer_id")
    private CertificateInfo issuer;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Extensions extensions;

    public String getIssuerAlias() {
    	if (this.issuer == null) {
    		return this.alias;
    	}
    	return this.issuer.getAlias();
    }
    
}
