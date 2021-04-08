package com.example.demo.model;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Configuration {
	private List<LogConfiguration> configurations;
}
