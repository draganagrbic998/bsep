package com.example.demo.service;

import com.example.demo.dto.CertificateInfoDTO;
import com.example.demo.mapper.CertificateInfoMapper;
import com.example.demo.model.CertificateInfo;
import com.example.demo.repository.CertificateInfoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificateInfoService {

    private final CertificateInfoRepository certificateInfoRepository;
    private final CertificateInfoMapper mapper;

    public CertificateInfoService(CertificateInfoRepository certificateInfoRepository,
                                  CertificateInfoMapper certificateInfoMapper) {
        this.certificateInfoRepository = certificateInfoRepository;
        this.mapper = certificateInfoMapper;
    }

    public List<CertificateInfoDTO> findAll() {
        return certificateInfoRepository.findAll().stream()
                .map(ci -> mapper.mapToDto(ci, 0)).collect(Collectors.toList());
    }

    public Page<CertificateInfoDTO> findAll(Pageable pageable) {
        return  certificateInfoRepository.findAll(pageable)
                .map(certificateInfo -> mapper.mapToDto(certificateInfo, 0));
    }

    public CertificateInfoDTO findById(Long id){
        return mapper.mapToDto(certificateInfoRepository.findById(id).orElse(null));
    }

    public CertificateInfoDTO findByAlias(String alias) {
        return mapper.mapToDto(certificateInfoRepository.findByAlias(alias));
    }

    public CertificateInfoDTO findByAliasIgnoreCase(String alias) {
        return mapper.mapToDto(certificateInfoRepository.findFirstByAliasContainingIgnoreCase(alias));
    }

    public CertificateInfoDTO save(CertificateInfo certInfo){
        return mapper.mapToDto(certificateInfoRepository.save(certInfo));
    }
}
