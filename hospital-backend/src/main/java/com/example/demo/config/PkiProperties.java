package com.example.demo.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "server.ssl")
public class PkiProperties {
	private String keyAlias;
    private String keystore;
    private String keystorePassword;
}
