package com.example.demo.mapper;

import com.example.demo.dto.CertificateInfoDTO;
import com.example.demo.model.CertificateInfo;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CertificateInfoMapper {

    public CertificateInfoDTO mapToDto(CertificateInfo certificateInfo) {
        return this.mapToDto(certificateInfo, 1);
    }

    public CertificateInfoDTO mapToDto(CertificateInfo certificateInfo, int mappingLevel) {
        if (certificateInfo == null) {
            return null;
        }
        CertificateInfoDTO certificateInfoDto = new CertificateInfoDTO();
        certificateInfoDto.setId(certificateInfo.getId());
        if (mappingLevel > 0) {
            certificateInfoDto.setIssued(certificateInfo.getIssued().stream()
                    .map(ci -> this.mapToDto(ci, mappingLevel - 1))
                    .collect(Collectors.toList()));
        }
        certificateInfoDto.setNumIssued(certificateInfo.getIssued().size());
        certificateInfoDto.setAlias(certificateInfo.getAlias());
        certificateInfoDto.setIssuerAlias(certificateInfo.getIssuerAlias());
        certificateInfoDto.setCommonName(certificateInfo.getCommonName());
        certificateInfoDto.setSerialNumber(certificateInfo.getSerialNumber());
        certificateInfoDto.setOrganization(certificateInfo.getOrganization());
        certificateInfoDto.setOrganizationUnit(certificateInfo.getOrganizationUnit());
        certificateInfoDto.setCountry(certificateInfo.getCountry());
        certificateInfoDto.setStartDate(certificateInfo.getStartDate());
        certificateInfoDto.setEndDate(certificateInfo.getEndDate());
        certificateInfoDto.setRevoked(certificateInfo.isRevoked());
        certificateInfoDto.setRevocationReason(certificateInfo.getRevocationReason());
        certificateInfoDto.setRevocationDate(certificateInfo.getRevocationDate());
        certificateInfoDto.setCA(certificateInfo.isCA());
        certificateInfoDto.setEmail(certificateInfo.getEmail());
        certificateInfoDto.setTemplate(certificateInfo.getTemplate());
        return certificateInfoDto;
    }

}
