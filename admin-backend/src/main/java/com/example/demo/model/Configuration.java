package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Configuration {
	private List<LogConfiguration> configurations;
}