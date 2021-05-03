package com.example.demo.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "pki")
public class PkiProperties {
	private String root;
	private String keystore;
	private String keystorePassword;
}
