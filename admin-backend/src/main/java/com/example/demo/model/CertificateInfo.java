package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class CertificateInfo {
	
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
    private String country;

    @NotBlank
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Template template;

    @NotNull
	private boolean basicConstraints;
	private String extendedKeyUsage;
	private String keyUsage;
	
    private String issuerAlias;

    @NotNull
    private boolean isCA;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private boolean revoked;

    @Temporal(TemporalType.TIMESTAMP)
    private Date revocationDate;

    private String revocationReason;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CertificateInfo> issued;

    @OneToOne(cascade = CascadeType.ALL)
    private Extensions extensions;

    public void addIssued(CertificateInfo certificateInfo) {
        if (!certificateInfo.issuerAlias.equalsIgnoreCase(this.alias)) {
            certificateInfo.issuerAlias = this.alias;
        }
        this.issued.add(certificateInfo);
    }

}
