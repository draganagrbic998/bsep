package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Configuration {

	@NotNull(message = "Configuration cannot be null")
	private List<LogConfiguration> configurations = new ArrayList<>();
	
}
