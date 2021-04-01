package com.example.demo.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import org.apache.http.client.HttpClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableTransactionManagement
public class AppConfig {

	@Autowired
	private PkiProperties pkiProperties;

	@Bean
	public KieContainer getKieContainer() {
		return KieServices.Factory.get().getKieClasspathContainer();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		try {
			File file = ResourceUtils.getFile(this.pkiProperties.getKeystore());
			KeyStore keyStore = KeyStore.getInstance("JKS");
			InputStream inputStream = new FileInputStream(file);
			keyStore.load(inputStream, this.pkiProperties.getKeystorePassword().toCharArray());

			SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
					new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
							.loadKeyMaterial(keyStore, this.pkiProperties.getKeystorePassword().toCharArray()).build(),
					NoopHostnameVerifier.INSTANCE);

			HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory)
					.setMaxConnTotal(5).setMaxConnPerRoute(5).build();

			HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
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
