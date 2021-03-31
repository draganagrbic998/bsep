package com.example.demo.service;

import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.model.Log;
import com.example.demo.repository.LogRepository;
import com.example.demo.utils.Configuration;
import com.example.demo.utils.Constants;
import com.example.demo.utils.LogConfiguration;
import com.google.gson.Gson;

@Service
@Transactional(readOnly = true)
public class LogService {

	@Autowired
	private LogRepository logRepository;
	
	@Autowired
	private LogMapper logMapper;
		
	@Autowired
	private LogEventService eventService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostConstruct
	public void init() {
		try {
			Gson gson = new Gson();
            File file = ResourceUtils.getFile("classpath:" + Constants.CONFIGURATION);
			Configuration configuration = gson.fromJson(new FileReader(file), Configuration.class);
		
			for (LogConfiguration lc: configuration.getConfigurations()) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							readLogs(lc.getPath(), lc.getInterval(), lc.getRegExp());
						} 
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Page<Log> findAll(Pageable pageable, LogSearchDTO searchDTO) {
		return this.logRepository.findAll(pageable, 
				searchDTO.getStatus(), 
				searchDTO.getDescription(), 
				searchDTO.getUserName(), 
				searchDTO.getComputerName(), 
				searchDTO.getServiceName());
	}

	@Transactional(readOnly = false)
	public List<Log> save(List<Log> logs) {
		return this.logRepository.saveAll(logs);
	}
	
	private void readLogs(String path, long interval, String regExp) throws InterruptedException {
		while (true) {
			List<LogMeasureDTO> logsDTO = this.restTemplate.exchange(path, HttpMethod.GET, null, new ParameterizedTypeReference<List<LogMeasureDTO>>() {}).getBody();
			List<Log> logs = logsDTO.stream().map(x -> this.logMapper.map(x)).collect(Collectors.toList());
			logs = this.save(logs);
			logs.forEach(x -> this.eventService.addLog(x));
			Thread.sleep(interval);	
		}
	}
	
}
