package com.example.demo.repository;

import com.example.demo.model.CertificateInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateInfoRepository extends JpaRepository<CertificateInfo, Long> {

    @Query("select ci from CertificateInfo ci where lower(ci.alias) like lower(:alias) ")
    public CertificateInfo findByAlias(String alias);

    @Query("select ci from CertificateInfo ci where ci.issuer is not null and ci.issuer.id = :id")
    public List<CertificateInfo> findIssued(long id);

}
