package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PublicKey;
import java.util.Date;

@Data
@NoArgsConstructor
public class SubjectData {
    private PublicKey publicKey;
    private X500Name x500name;
    private String serialNumber;
    private Date startDate;
    private Date endDate;
}
