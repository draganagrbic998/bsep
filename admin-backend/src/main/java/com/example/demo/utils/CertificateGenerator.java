package com.example.demo.utils;

import com.example.demo.model.Extensions;
import com.example.demo.model.IssuerData;
import com.example.demo.model.SubjectData;
import com.example.demo.model.Template;
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

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CertificateGenerator {


    public X509Certificate generateCertificate(SubjectData subjectData,
                                               IssuerData issuerData,
                                               KeyPair keyPair,
                                               boolean isSelfSigned,
                                               java.security.cert.Certificate issuer,
                                               Extensions extensions) {
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
            
            if (extensions.getBasicConstraints() != null) {
                certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(extensions.getBasicConstraints()));
            }

            if (extensions.getKeyPurposeIds() != null && extensions.getKeyPurposeIds().size() > 0) {
                KeyPurposeId[] ids = extensions.getEntityKeyPurposeIds().toArray(new KeyPurposeId[0]);
                certGen.addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(ids));
            }

            if (extensions.getKeyUsage() != null && extensions.getKeyUsage() != 0) {
                certGen.addExtension(Extension.keyUsage, true, new KeyUsage(extensions.getKeyUsage()));
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