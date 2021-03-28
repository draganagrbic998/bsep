package com.example.demo;

import com.example.demo.dto.CertificateInfoDTO;
import com.example.demo.model.CertificateInfo;
import com.example.demo.model.IssuerData;
import com.example.demo.model.SubjectData;
import com.example.demo.model.Template;
import com.example.demo.repository.CertificateInfoRepository;
import com.example.demo.service.CertificateInfoService;
import com.example.demo.service.CertificateService;
import com.example.demo.service.KeyStoreService;
import com.example.demo.util.CertificateGenerator;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.Date;

@Component
public class Setup implements ApplicationRunner {

    @Autowired
    private KeyStoreService keyStoreService;

    @Autowired
    private CertificateInfoRepository certificateInfoRepository;

    @Autowired
    private CertificateService certificateService;

    @Autowired
    private CertificateGenerator certificateGenerator;

    @Value("${PKI.keystore_path}")
    public String keystore_path;

    @Value("${PKI.keystore_name}")
    public String keystore_name;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        File keystore = new File(this.keystore_path + this.keystore_name);
        CertificateInfo ci = certificateInfoRepository.findFirstByAliasContainingIgnoreCase("root");
        if (ci == null) {
            keystore.delete();
        }
        if(!keystore.exists()){
            keyStoreService.createKeyStore();
        }else {
            keyStoreService.loadKeyStore();
        }
        Certificate root = keyStoreService.readCertificate("root");
        if(root == null){
            createRootCA();
            keyStoreService.saveKeyStore();
        }
//        Certificate PKI = keyStoreService.readCertificate("PKI");
//        if(PKI == null){
//            createPKI();
//            keyStoreService.saveKeyStore();
//        }
    }

    private void createRootCA(){
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "root");
        builder.addRDN(BCStyle.O, "BSEP");
        builder.addRDN(BCStyle.OU, "CA");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "tim1@email.com");
        X500Name rootName =  builder.build();
        KeyPair keyPair = certificateService.generateKeyPair();
        SubjectData subjectData = new SubjectData();
        IssuerData issuerData = new IssuerData();

        issuerData.setX500name(rootName);
        issuerData.setPrivateKey(keyPair.getPrivate());

        subjectData.setX500name(rootName);
        subjectData.setPublicKey(keyPair.getPublic());

        Date[] dates = certificateService.generateDates(120);
        subjectData.setStartDate(dates[0]);
        subjectData.setEndDate(dates[1]);

        CertificateInfo certificateInfo = generateCertificateInfoEntity(subjectData);
        subjectData.setSerialNumber(certificateInfo.getId().toString());
        Certificate rootCertificate = certificateGenerator.generateCertificate(subjectData, issuerData, Template.SUB_CA, keyPair, true, null);
        keyStoreService.savePrivateKey("root", new Certificate[]{rootCertificate}, keyPair.getPrivate());
    }

    private CertificateInfo generateCertificateInfoEntity(SubjectData subjectData){
        CertificateInfo certInfo = new CertificateInfo();

        certInfo.setCommonName("root");
        certInfo.setAlias("root");
        certInfo.setStartDate(subjectData.getStartDate());
        certInfo.setEndDate(subjectData.getEndDate());
        certInfo.setRevoked(false);
        certInfo.setRevocationReason("");
        certInfo.setOrganization("BSEP");
        certInfo.setCountry("RS");
        certInfo.setEmail("tim1@email.com");
        certInfo.setOrganizationUnit("CA");
        certInfo.setIssuerAlias("root");
        certInfo.setCA(true);
        certInfo.setTemplate(Template.SUB_CA);
        return certificateInfoRepository.save(certInfo);
    }
}
