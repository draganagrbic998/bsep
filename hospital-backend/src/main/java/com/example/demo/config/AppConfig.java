package com.example.demo.config;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class AppConfig {

	@Bean
	public KieContainer getKieContainer() {
		return KieServices.Factory.get().getKieClasspathContainer();
	}

}
