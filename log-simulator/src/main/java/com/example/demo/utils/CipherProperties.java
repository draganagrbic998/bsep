package com.example.demo.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "cipher")
public class CipherProperties {
	private String ipsPath;
    private String keystorePath;
    private String keystorePassword;
}
