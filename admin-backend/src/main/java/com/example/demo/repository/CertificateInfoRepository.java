package com.example.demo.repository;

import com.example.demo.model.CertificateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateInfoRepository extends JpaRepository<CertificateInfo, Long> {

    @Query("select ci from CertificateInfo ci where lower(ci.alias) like lower(:alias) ")
    public CertificateInfo findByAliasIgnoreCase(String alias);

}
