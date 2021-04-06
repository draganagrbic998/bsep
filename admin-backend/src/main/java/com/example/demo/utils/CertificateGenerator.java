package com.example.demo.utils;

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
	
	private Map<String, Integer> keyUsageMapping = new HashMap<>();
	
	public CertificateGenerator() {
		this.keyUsageMapping.put("cRLSign", 1);
		this.keyUsageMapping.put("digitalSignature", 7);
		this.keyUsageMapping.put("keyCertSign", 2);
		this.keyUsageMapping.put("encipherOnly", 0);
		this.keyUsageMapping.put("keyEncipherment", 5);
		this.keyUsageMapping.put("keyAgreement", 3);
		this.keyUsageMapping.put("nonRepudiation", 6);
	}

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, Template template, KeyPair keyPair,
    		boolean isSelfSigned, java.security.cert.Certificate issuer, boolean isBasicConstaints, String extendedKeyUsage, List<String> keyUsage) {
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

            certGen.addExtension(Extension.basicConstraints, true, new BasicConstraints(isBasicConstaints));
            if (extendedKeyUsage != null && extendedKeyUsage.equalsIgnoreCase("id_kp_clientAuth")) {
                certGen.addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_clientAuth));
            }
            else if (extendedKeyUsage != null && extendedKeyUsage.equalsIgnoreCase("id_kp_serverAuth")) {
                certGen.addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
            }
            int temp = keyUsage.stream().map(x -> 1 << this.keyUsageMapping.get(x)).reduce(0, (subtotal, element) -> subtotal | element);
            certGen.addExtension(Extension.keyUsage, true, new KeyUsage(temp));

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