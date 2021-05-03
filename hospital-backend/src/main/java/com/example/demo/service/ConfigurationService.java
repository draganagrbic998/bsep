package com.example.demo.service;

import com.example.demo.model.Configuration;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.utils.Constants;
import com.example.demo.utils.Logger;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
@AllArgsConstructor
public class ConfigurationService {

	public final static String CONFIGURATION_FILE = Constants.RESOURCES_FOLDER + "configuration.json";
    private final static Gson GSON = new Gson();

    private final LogService logService;
    private final Logger logger;

    public Configuration getConfiguration() {
    	Configuration configuration;
        try {
            FileReader reader = new FileReader(new File(CONFIGURATION_FILE));
            configuration = GSON.fromJson(reader, Configuration.class);
            reader.close();
            return configuration;
        } 
        catch (Exception e) {
            configuration = new Configuration();
        }
        this.logger.write(LogStatus.SUCCESS, "Configuration successfully fetched.");   
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
    	try {
            FileWriter writer = new FileWriter(CONFIGURATION_FILE);
            GSON.toJson(configuration, writer);
            writer.close();
            this.logService.readConfiguration();
            this.logger.write(LogStatus.SUCCESS, "Configuration successfully changed.");   
    	}
    	catch(Exception e) {
            this.logger.write(LogStatus.ERROR, "Error occurred while changing configuration.");   
    		throw new RuntimeException(e);
    	}
    }
}
