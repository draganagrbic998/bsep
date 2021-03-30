package com.example.demo.repository;

import com.example.demo.model.CertificateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CertificateInfoRepository extends JpaRepository<CertificateInfo, Long> {

    public List<CertificateInfo> findAllByEndDateBeforeAndRevoked(Date date, boolean isRevoked);
    public CertificateInfo findByAlias(String alias);
    public CertificateInfo findFirstByAliasContainingIgnoreCase(String alias);

}
