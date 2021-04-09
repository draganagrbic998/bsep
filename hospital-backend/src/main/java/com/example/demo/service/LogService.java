package com.example.demo.service;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.LogMeasureDTO;
import com.example.demo.dto.LogSearchDTO;
import com.example.demo.mapper.LogMapper;
import com.example.demo.model.Configuration;
import com.example.demo.model.Log;
import com.example.demo.model.LogConfiguration;
import com.example.demo.repository.LogRepository;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class LogService {

	private final LogRepository logRepository;
	private final LogMapper logMapper;
	private final LogEventService eventService;
	private final RestTemplate restTemplate;
	
	@PostConstruct
	public void init() {
		try {
			Gson gson = new Gson();
            File file = ResourceUtils.getFile("classpath:configuration.json");
			Configuration configuration = gson.fromJson(new FileReader(file), Configuration.class);
		
			for (LogConfiguration lc: configuration.getConfigurations()) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						readLogs(lc.getPath(), lc.getInterval(), lc.getRegExp());
					}
				}).start();
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
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
	public List<Log> save(List<Log> logs) {
		return this.logRepository.saveAll(logs);
	}
	
	private void readLogs(String path, long interval, String regExp) {
		while (true) {
			try {
				List<LogMeasureDTO> logsDTO = this.restTemplate.exchange(path, HttpMethod.GET, null, 
					new ParameterizedTypeReference<List<LogMeasureDTO>>() {}).getBody();
				List<Log> logs = logsDTO.stream().filter(x -> x.getText().matches(regExp)).map(x -> this.logMapper.map(x)).collect(Collectors.toList());
				logs = this.save(logs);
				logs.forEach(x -> this.eventService.addLog(x));
				Thread.sleep(interval);	
			}
			catch(Exception e) {
				;
			}
		}
	}
	
}
