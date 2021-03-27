package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class CertificateInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String alias;

    private String issuerAlias;

    private String commonName;

    private String serialNumber;

    private String organisation;

    private String organisationUnit;

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


}
