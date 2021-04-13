package com.example.demo.service;

import com.example.demo.model.Configuration;
import com.example.demo.utils.Constants;
import com.google.gson.Gson;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ConfigurationService {

    private final Gson GSON = new Gson();

    public Configuration get() {
        try {
            File file = new ClassPathResource(Constants.CONFIGURATION_FILE).getFile();
            FileReader reader = new FileReader(file);
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
            File file;
            ClassPathResource resource = new ClassPathResource(Constants.CONFIGURATION_FILE);
            if (resource.isFile()) {
                file = resource.getFile();
            } 
            else {
                file = new File(resource.getPath());
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file);
            GSON.toJson(configuration, Configuration.class, writer);
            writer.close();
    	}
    	catch(Exception e) {
    		throw new RuntimeException(e);
    	}
    }
}
