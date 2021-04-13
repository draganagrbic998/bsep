package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import com.example.demo.model.Configuration;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConfigurationTarget {
	
	@NotBlank(message = "Url cannot be blank")
    private String url;
    private Configuration configuration;

}
