package com.example.demo.service;

import com.example.demo.model.Configuration;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class ConfigurationService {

    private final String config = "%s/configuration";

    private final RestTemplate restTemplate;

    public Configuration getConfiguration(String url) {
        return restTemplate.getForEntity(String.format(config, url), Configuration.class).getBody();
    }
}
