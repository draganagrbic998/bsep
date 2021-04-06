package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "certificate_table")
public class CertificateInfo {

	//proveri jel ovo ok
	private boolean basicConstraints;
	private String extendedKeyUsage;
	private String keyUsage;
	
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CertificateInfo> issued;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date revocationDate;

    private boolean isCA;

    private String email;

    @Enumerated(EnumType.STRING)
    private Template template;

    public void addIssued(CertificateInfo certificateInfo) {
        if (!certificateInfo.issuerAlias.equalsIgnoreCase(this.alias)) {
            certificateInfo.issuerAlias = this.alias;
        }
        this.issued.add(certificateInfo);
    }

}
