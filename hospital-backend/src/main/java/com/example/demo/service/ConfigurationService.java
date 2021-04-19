package com.example.demo.service;

import com.example.demo.model.Configuration;
import com.example.demo.utils.Constants;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
@AllArgsConstructor
public class ConfigurationService {

    private final Gson GSON = new Gson();
    private final LogService logService;

    public Configuration get() {
        try {
            FileReader reader = new FileReader(new File(Constants.CONFIGURATION_FILE));
            Configuration config = GSON.fromJson(reader, Configuration.class);
            reader.close();
            return config;
        } 
        catch (Exception e) {
            return new Configuration();
        }
    }

    public void set(Configuration configuration) {
    	try {
            FileWriter writer = new FileWriter(Constants.CONFIGURATION_FILE);
            GSON.toJson(configuration, writer);
            writer.close();
            this.logService.readConfiguration();
    	}
    	catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    }
}
