package com.example.demo.utils;

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

    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, KeyPair keyPair, Certificate issuer, 
    		boolean isSelfSigned, boolean isBasicConstaints, String extendedKeyUsage, List<String> keyUsage) {
    	
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
            
            if (isSelfSigned) {
                authorityKeyIdentifier = certificateExtensionUtils.createAuthorityKeyIdentifier(keyPair.getPublic());
            } 
            else {
                authorityKeyIdentifier = certificateExtensionUtils.createAuthorityKeyIdentifier(issuer.getPublicKey());
            }
            
            generator.addExtension(Extension.authorityKeyIdentifier, false, authorityKeyIdentifier);
            generator.addExtension(Extension.subjectAlternativeName, false, new GeneralNames(new GeneralName(GeneralName.dNSName, "localhost")));
            generator.addExtension(Extension.basicConstraints, true, new BasicConstraints(isBasicConstaints));
            
            if (extendedKeyUsage != null && extendedKeyUsage.equalsIgnoreCase("id_kp_clientAuth")) {
                generator.addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_clientAuth));
            }
            else if (extendedKeyUsage != null && extendedKeyUsage.equalsIgnoreCase("id_kp_serverAuth")) {
                generator.addExtension(Extension.extendedKeyUsage, true, new ExtendedKeyUsage(KeyPurposeId.id_kp_serverAuth));
            }
            
            int temp = keyUsage.stream().map(x -> 1 << this.keyUsageMapping.get(x)).reduce(0, (subtotal, element) -> subtotal | element);
            generator.addExtension(Extension.keyUsage, true, new KeyUsage(temp));

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