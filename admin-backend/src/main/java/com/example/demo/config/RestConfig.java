package com.example.demo.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.example.demo.utils.PkiProperties;
import com.example.demo.utils.RestTemplateErrorHandler;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

@Configuration
@AllArgsConstructor
public class RestConfig {

	private final PkiProperties pkiProperties;
	private final RestTemplateBuilder restTemplateBuilder;

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = this.restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
		
		try {
			KeyStore keyStore = KeyStore.getInstance("JKS");
			InputStream in = new FileInputStream(new File(this.pkiProperties.getRoot()));
			keyStore.load(in, this.pkiProperties.getKeystorePassword().toCharArray());
			in.close();

			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
					new SSLContextBuilder().loadTrustMaterial(keyStore, new TrustSelfSignedStrategy())
							.loadKeyMaterial(keyStore, this.pkiProperties.getKeystorePassword().toCharArray()).build(),
					NoopHostnameVerifier.INSTANCE);

			HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
					.setMaxConnTotal(5).setMaxConnPerRoute(5).build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
			requestFactory.setReadTimeout(10000);
			requestFactory.setConnectTimeout(10000);
			restTemplate.setRequestFactory(requestFactory);
			in.close();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		return restTemplate;
	}

}
