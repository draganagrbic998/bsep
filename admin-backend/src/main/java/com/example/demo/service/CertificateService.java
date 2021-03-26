package com.example.demo.service;

import com.example.demo.dto.CreateCertificateDto;
import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.model.IssuerData;
import org.springframework.stereotype.Service;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

@Service
public class CertificateService {

    private KeyStoreService keyStoreService;

    public CertificateService(KeyStoreService keyStoreService) {
        this.keyStoreService = keyStoreService;
    }

    public void createCertificate(CreateCertificateDto createCertificateDto) throws CertificateNotFoundException,
//            IssuerNotCAException,
//            IssuerNotValidException,
//            UnknownTemplateException,
//            AliasAlreadyTakenException
    {
        keyStoreService.loadKeyStore();
        Certificate[] issuerCertificateChain = keyStoreService.readCertificateChain(issuerAlias);
        IssuerData issuerData = keyStoreService.readIssuerFromStore(issuerAlias);

        X509Certificate issuer = (X509Certificate) issuerCertificateChain[0];
        if (!isCertificateValid(issuerAlias))
            throw new IssuerNotValidException();

        try {
            if (issuer.getBasicConstraints() == -1 || !issuer.getKeyUsage()[5]) {
                // Sertifikat nije CA
                // https://stackoverflow.com/questions/12092457/how-to-check-if-x509certificate-is-ca-certificate
                throw new IssuerNotCAException();
            }
        } catch (NullPointerException ignored) {
        }

        CertificateInfo certInfo = certificateInfoService.findByAliasIgnoreCase(alias);
        if (certInfo != null)
            throw new AliasAlreadyTakenException();

        KeyPair keyPair = KeyPairGenerator.generateKeyPair();
        assert keyPair != null;

        SubjectData subjectData = new SubjectData();
        subjectData.setX500name(subjectName);

        switch (template) {
            case "INTERMEDIATE_CA":
                return createX509Certificate_INTERMEDIATE_CA(issuerAlias, alias, issuerCertificateChain, issuerData, keyPair, subjectData);
            case "TLS_SERVER":
                return createX509Certificate_TLS_SERVER(issuerAlias, alias, issuerCertificateChain, issuerData, keyPair, subjectData);
            case "SIEM_CENTER":
                return createX509Certificate_SIEM_CENTER(issuerAlias, alias, issuerCertificateChain, issuerData, keyPair, subjectData);
            case "SIEM_AGENT":
                return createX509Certificate_SIEM_AGENT(issuerAlias, alias, issuerCertificateChain, issuerData, keyPair, subjectData);
            default:
                throw new UnknownTemplateException(template);
        }
    }
}
