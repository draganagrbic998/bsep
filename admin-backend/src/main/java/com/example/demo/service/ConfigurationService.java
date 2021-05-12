package com.example.demo.service;

import com.example.demo.model.Configuration;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.utils.AuthenticationProvider;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class ConfigurationService {

    private final static String CONFIG_URL = "%s/api/configuration";
    
    private final RestTemplate restTemplate;
    private final AuthenticationProvider authProvider;
    private final Logger logger;

    public Configuration getConfiguration(String url) {
    	try {
        	Configuration response = this.restTemplate.exchange(
    			String.format(CONFIG_URL, url), 
    			HttpMethod.GET, 
    			this.authProvider.getAuthEntity(null), 
    			Configuration.class).getBody();
            this.logger.write(LogStatus.SUCCESS, "Configuration successfully fetched.");   
        	return response;
    	}
    	catch(Exception e) {
            this.logger.write(LogStatus.ERROR, "Error occurred while fetching configuration.");   
    		throw e;
    	}
    }

    public void setConfiguration(String url, Configuration configuration) {
    	try {
        	this.restTemplate.exchange(
    			String.format(CONFIG_URL, url), 
    			HttpMethod.PUT, 
    			this.authProvider.getAuthEntity(configuration), 
    			Void.class);
            this.logger.write(LogStatus.SUCCESS, "Configuration successfully changed.");   
    	}
    	catch(Exception e) {
            this.logger.write(LogStatus.ERROR, "Error occurred while changing configuration.");   
    		throw e;
    	}
    }
}
