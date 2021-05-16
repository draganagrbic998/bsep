package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.mapper.LogMapper;
import com.example.demo.model.Configuration;
import com.example.demo.model.Log;
import com.example.demo.model.LogConfiguration;
import com.example.demo.repository.LogRepository;
import com.example.demo.service.event.LogEventService;
import com.example.demo.utils.Logger;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogService {

	private static long CONFIG_VERSION = 0;
    private final static Gson GSON = new Gson();

	private final LogRepository logRepository;
	private final LogEventService eventService;
	private final LogMapper logMapper;
	
	@Transactional(readOnly = true)
	public Page<Log> findAll(Pageable pageable, String mode, String status, String ipAddress, String description, Date date) {
		return this.logRepository.findAll(pageable, 
				mode, status, ipAddress, description,
				date == null ? "empty" : new SimpleDateFormat("yyyy-MM-dd").format(date));
	}

	@PostConstruct
	public void init() {
		this.readConfiguration();
	}
	
	public void readConfiguration() {
		try {
			FileReader reader = new FileReader(new File(ConfigurationService.CONFIGURATION_FILE));
			Configuration configuration = GSON.fromJson(reader, Configuration.class);
			reader.close();
			++CONFIG_VERSION;
			
			for (LogConfiguration config: configuration.getConfigurations()) {
				if (new File(config.getPath()).exists())
					new Thread(() -> this.readLogs(CONFIG_VERSION, config.getPath(), config.getInterval(), config.getRegExp())).start();
			}
			if (!configuration.getConfigurations().stream().anyMatch(config -> config.getPath().equals(Logger.LOG_FILE)))
				new Thread(() -> this.readLogs(CONFIG_VERSION, Logger.LOG_FILE, 1000, ".*")).start();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	private void readLogs(long configVersion, String path, long interval, String regExp) {
		while (configVersion == CONFIG_VERSION) {
			try {
				List<String> lines = this.readLines(path, regExp);
				List<Log> logs = lines.stream().map(log -> this.logMapper.map(log))
					.filter(log -> log.getDate() != null || log.getMode() != null || log.getStatus() != null || log.getIpAddress() != null || log.getDescription() != null)
					.collect(Collectors.toList());
				logs = this.save(logs);
				logs.forEach(log -> this.eventService.addLog(log));
				Thread.sleep(interval);	
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
		
	private List<String> readLines(String path, String regExp) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(path));
		List<String> lines = new ArrayList<>();
		String line;
		while ((line = reader.readLine()) != null) {
			if (line.matches(regExp))
				lines.add(line);
		}
		reader.close();
		FileWriter writer = new FileWriter(path);
		writer.close();
		return lines;
	}

	@Transactional(readOnly = false)
	private List<Log> save(List<Log> logs) {
		return this.logRepository.saveAll(logs);
	}

}
