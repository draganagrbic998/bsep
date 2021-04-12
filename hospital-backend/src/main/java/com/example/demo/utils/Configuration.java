package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Configuration {

	@NotNull
	private List<LogConfiguration> configurations = new ArrayList<>();
}
