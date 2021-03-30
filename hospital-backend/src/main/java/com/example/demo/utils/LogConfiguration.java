package com.example.demo.utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogConfiguration {

	private String path;
	private long interval;
	private String regExp;
		
}
