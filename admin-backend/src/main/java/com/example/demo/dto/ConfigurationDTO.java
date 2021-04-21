package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import com.example.demo.model.Configuration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConfigurationDTO {
	
	@NotBlank(message = "Url cannot be blank")
    private String url;
    private Configuration configuration;

}
