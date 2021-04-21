package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class Configuration {

	@NotNull(message = "Configuration cannot be null")
	private List<LogConfiguration> configurations = new ArrayList<>();
	
}
