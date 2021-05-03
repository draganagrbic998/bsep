package com.example.demo.utils;

import com.example.demo.model.Extensions;
import com.example.demo.model.IssuerData;
import com.example.demo.model.SubjectData;

import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;
import java.security.cert.Certificate;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;

@Component
public class CertificateGenerator {

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData,
    	KeyPair keyPair, Certificate issuer, boolean isSelfSigned, Extensions extensions) {
    	
        try {
            Security.addProvider(new BouncyCastleProvider());
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");

            ContentSigner signer = builder.build(issuerData.getPrivateKey());
            X509v3CertificateBuilder generator = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                new BigInteger(subjectData.getSerialNumber()),
                subjectData.getStartDate(),
                subjectData.getEndDate(),
                subjectData.getX500name(),
                subjectData.getPublicKey());

            JcaX509ExtensionUtils certificateExtensionUtils = new JcaX509ExtensionUtils();
            SubjectKeyIdentifier subjectKeyIdentifier = certificateExtensionUtils.createSubjectKeyIdentifier(keyPair.getPublic());
            generator.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);
            AuthorityKeyIdentifier authorityKeyIdentifier;
            
            if (isSelfSigned)
            	authorityKeyIdentifier = certificateExtensionUtils.createAuthorityKeyIdentifier(keyPair.getPublic());
            else
            	authorityKeyIdentifier = certificateExtensionUtils.createAuthorityKeyIdentifier(issuer.getPublicKey());
            
            generator.addExtension(Extension.authorityKeyIdentifier, false, authorityKeyIdentifier);
            generator.addExtension(Extension.subjectAlternativeName, false, new GeneralNames(new GeneralName(GeneralName.dNSName, "localhost")));
            
            if (extensions.getBasicConstraints() != null)
            	generator.addExtension(Extension.basicConstraints, true, new BasicConstraints(extensions.getBasicConstraints()));

            if (extensions.getKeyPurposeIds() != null && extensions.getKeyPurposeIds().size() > 0) {
                KeyPurposeId[] ids = extensions.getEntityKeyPurposeIds().toArray(new KeyPurposeId[0]);
                generator.addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(ids));
            }

            if (extensions.getKeyUsage() != null && extensions.getKeyUsage() != 0)
            	generator.addExtension(Extension.keyUsage, true, new KeyUsage(extensions.getKeyUsage()));

            X509CertificateHolder holder = generator.build(signer);
            JcaX509CertificateConverter converter = new JcaX509CertificateConverter();
            converter = converter.setProvider("BC");
            return converter.getCertificate(holder);
        }
        catch (Exception e) {
        	throw new RuntimeException(e);
        }
    }
}