package com.example.demo.dto;

import com.example.demo.model.Configuration;
import lombok.Data;

@Data
public class ConfigurationTarget {
    private String url;
    private Configuration configuration;
}
