package com.example.demo.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "pki")
public class PkiProperties {

    private String keystorePath;
    private String keystoreName;
    private String keystorePassword;

}