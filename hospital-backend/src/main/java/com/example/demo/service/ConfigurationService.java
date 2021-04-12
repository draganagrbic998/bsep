package com.example.demo.service;

import com.example.demo.utils.Configuration;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URL;

@Service
public class ConfigurationService {

    private final Gson g = new Gson();

    public Configuration get() {
        try {
            File f = new ClassPathResource("configuration.json").getFile();
            return g.fromJson(new FileReader(f), Configuration.class);
        } catch (IOException e) {
            return new Configuration();
        }
    }

    public void set(Configuration configuration) throws IOException {
        File f;
        ClassPathResource confResource = new ClassPathResource("configuration.json");
        if (confResource.isFile()) {
            f = confResource.getFile();
        } else {
            f = new File(confResource.getPath());
            f.createNewFile();
        }

        FileWriter fw = new FileWriter(f, false);
        g.toJson(configuration, Configuration.class, fw);
    }
}
