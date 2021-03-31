package com.example.demo.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.KeyStore;

import org.apache.http.client.HttpClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
@EnableTransactionManagement
public class AppConfig {

	private final PkiProperties pkiProperties;

	@Autowired
	public AppConfig(PkiProperties pkiProperties) {
		this.pkiProperties = pkiProperties;
	}

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		try {
			File keystore = new File(Path.of(this.pkiProperties.getKeystorePath(),
					this.pkiProperties.getKeystoreName()).toString());

			KeyStore keyStore = KeyStore.getInstance("JKS");
			InputStream inputStream = new FileInputStream(keystore);
			keyStore.load(inputStream, this.pkiProperties.getKeystorePassword().toCharArray());

			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
					new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
							.loadKeyMaterial(keyStore, this.pkiProperties.getKeystorePassword().toCharArray()).build(),
					NoopHostnameVerifier.INSTANCE);

			HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
					.setMaxConnTotal(5).setMaxConnPerRoute(5).build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
					httpClient);
			requestFactory.setReadTimeout(10000);
			requestFactory.setConnectTimeout(10000);
			restTemplate.setRequestFactory(requestFactory);
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return restTemplate;
	}

}
