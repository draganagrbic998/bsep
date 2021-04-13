package com.example.demo.service;

import com.example.demo.model.Configuration;
import com.example.demo.utils.AuthenticationProvider;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class ConfigurationService {

    private static final String CONFIG_URL = "%s/api/configuration";
    private final RestTemplate restTemplate;
    private final AuthenticationProvider authProvider;

    public Configuration get(String url) {
    	return this.restTemplate.exchange(
    			String.format(CONFIG_URL, url), 
    			HttpMethod.GET, 
    			this.authProvider.getAuthEntity(null), 
    			Configuration.class).getBody();
    }

    public void set(String url, Configuration configuration) {
    	this.restTemplate.exchange(
    			String.format(CONFIG_URL, url), 
    			HttpMethod.PUT, 
    			this.authProvider.getAuthEntity(configuration), 
    			Void.class);
    }
}
