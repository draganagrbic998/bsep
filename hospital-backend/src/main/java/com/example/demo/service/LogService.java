package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.LogSearchDTO;
import com.example.demo.mapper.LogMapper;
import com.example.demo.model.Configuration;
import com.example.demo.model.Log;
import com.example.demo.model.LogConfiguration;
import com.example.demo.repository.LogRepository;
import com.example.demo.utils.Constants;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class LogService {

    private final static Gson GSON = new Gson();

	private final LogRepository logRepository;
	private final LogMapper logMapper;
	private final LogEventService eventService;

	public Page<Log> findAll(Pageable pageable, LogSearchDTO searchDTO) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String stringDate = searchDTO.getDate() == null ? "empty" : format.format(searchDTO.getDate());
		return this.logRepository.findAll(pageable, 
				searchDTO.getMode(), 
				searchDTO.getStatus(), 
				searchDTO.getIpAddress(),
				searchDTO.getDescription(),
				stringDate);
	}

	@Transactional(readOnly = false)
	private List<Log> save(List<Log> logs) {
		return this.logRepository.saveAll(logs);
	}

	@PostConstruct
	public void init() {
		try {
            File file = new ClassPathResource(Constants.CONFIGURATION_FILE).getFile();
			Configuration configuration = GSON.fromJson(new FileReader(file), Configuration.class);
		
			for (LogConfiguration lc: configuration.getConfigurations()) {
				new Thread(() -> this.readLogs(lc.getPath(), lc.getInterval(), lc.getRegExp())).start();
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
		
	private void readLogs(String path, long interval, String regExp) {
		while (true) {
			try {
				List<String> lines = this.readAll(path, regExp);
				List<Log> logs = lines.stream().map(x -> this.logMapper.map(x)).collect(Collectors.toList());
				logs = this.save(logs);
				logs.forEach(x -> this.eventService.addLog(x));
				Thread.sleep(interval);	
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<String> readAll(String path, String regExp) throws IOException {
		List<String> lines = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(path));
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

}
