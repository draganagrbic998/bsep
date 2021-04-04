package com.example.demo.config;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.KeyStore;

@Configuration
@EnableTransactionManagement
public class AppConfig {

	private final PkiProperties pkiProperties;
	private final RestTemplateBuilder restTemplateBuilder;

	@Autowired
	public AppConfig(PkiProperties pkiProperties,
					 RestTemplateBuilder restTemplateBuilder) {
		this.pkiProperties = pkiProperties;
		this.restTemplateBuilder = restTemplateBuilder;
	}

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();

		try {
			File file = new File(Path.of(this.pkiProperties.getKeystorePath(), this.pkiProperties.getKeystoreName()).toString());
			KeyStore keyStore = KeyStore.getInstance("JKS");
			InputStream inputStream = new FileInputStream(file);
			keyStore.load(inputStream, this.pkiProperties.getKeystorePassword().toCharArray());

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
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		return restTemplate;
	}

	@Bean
	public SpringTemplateEngine springTemplateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(htmlTemplateResolver());
		return templateEngine;
	}
	@Bean
	public SpringResourceTemplateResolver htmlTemplateResolver(){
		SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();
		emailTemplateResolver.setPrefix("classpath:/templates/");
		emailTemplateResolver.setSuffix(".html");
		emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
		emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
		return emailTemplateResolver;
	}
}
