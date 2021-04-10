package com.example.demo.utils.certificate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PrivateKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuerData {
    private PrivateKey privateKey;
    private X500Name x500name;
}
