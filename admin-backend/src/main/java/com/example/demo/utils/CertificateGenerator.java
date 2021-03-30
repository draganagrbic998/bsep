package com.example.demo.utils;

import com.example.demo.model.IssuerData;
import com.example.demo.model.SubjectData;
import com.example.demo.model.Template;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;


@Component
@NoArgsConstructor
public class CertificateGenerator {

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, Template template, KeyPair keyPair, boolean isSelfSigned, java.security.cert.Certificate issuer) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");

            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());
            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            JcaX509ExtensionUtils certificateExtensionUtils = new JcaX509ExtensionUtils();
            SubjectKeyIdentifier subjectKeyIdentifier = certificateExtensionUtils.createSubjectKeyIdentifier(keyPair.getPublic());
            certGen.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);
            AuthorityKeyIdentifier authorityKeyIdentifier;
            
            if (isSelfSigned) {
                authorityKeyIdentifier = certificateExtensionUtils.createAuthorityKeyIdentifier(keyPair.getPublic());
            } 
            else {
                authorityKeyIdentifier = certificateExtensionUtils.createAuthorityKeyIdentifier(issuer.getPublicKey());
            }
            
            certGen.addExtension(Extension.authorityKeyIdentifier, false, authorityKeyIdentifier);
            certGen.addExtension(Extension.subjectAlternativeName, false, new GeneralNames(new GeneralName(GeneralName.dNSName, "localhost")));

            switch (template) {
                case SUB_CA:
                    certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));
                    certGen.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.cRLSign | KeyUsage.digitalSignature | KeyUsage.keyCertSign));
                    break;
                case TLS:
                    certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));
                    certGen.addExtension(Extension.keyUsage, true,
                            new KeyUsage( KeyUsage.nonRepudiation | KeyUsage.digitalSignature | KeyUsage.encipherOnly | KeyUsage.keyEncipherment | KeyUsage.keyAgreement));
                    certGen.addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
                    break;
                case USER:
                    certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(false));
                    certGen.addExtension(Extension.keyUsage, true, new KeyUsage(KeyUsage.nonRepudiation | KeyUsage.digitalSignature | KeyUsage.keyEncipherment));
                    certGen.addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_clientAuth));
                    break;
            }

            X509CertificateHolder certHolder = certGen.build(contentSigner);
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");
            return certConverter.getCertificate(certHolder);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}