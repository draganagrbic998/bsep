package com.example.demo.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

@Component
@AllArgsConstructor
public class LogRemover {
	private final Logger logger;

	@Scheduled(fixedDelay = 60000)
	public void scheduleTaskUsingCronExpression() {
		this.logger.clearLogFile();
	}
}
