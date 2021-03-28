package com.example.demo.service;

import com.example.demo.model.CertificateInfo;
import com.example.demo.repository.CertificateInfoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateInfoService {

    private final CertificateInfoRepository certificateInfoRepository;

    public CertificateInfoService(CertificateInfoRepository certificateInfoRepository) {
        this.certificateInfoRepository = certificateInfoRepository;
    }

    public List<CertificateInfo> findAll() {
        return certificateInfoRepository.findAll();
    }

    public Page<CertificateInfo> findAll(Pageable pageable) {
        return  certificateInfoRepository.findAll(pageable);
    }

    public CertificateInfo findById(Long id){
        return certificateInfoRepository.findById(id).orElse(null);
    }

    public CertificateInfo findByAlias(String alias) { return certificateInfoRepository.findByAlias(alias); }

    public CertificateInfo findByAliasIgnoreCase(String alias){ return certificateInfoRepository.findFirstByAliasContainingIgnoreCase(alias); }

    public CertificateInfo save(CertificateInfo certInfo){
        return certificateInfoRepository.save(certInfo);
    }
}
