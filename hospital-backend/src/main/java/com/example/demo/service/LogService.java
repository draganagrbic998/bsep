package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

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
						readLogs(lc.getPath(), lc.getInterval(), lc.getRegExp());
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
	public Log save(Log log) {
		return this.logRepository.save(log);
	}
	
	private void readLogs(String path, long interval, String regExp) {
		BufferedReader reader = null;
		String line;
		while (true) {
			try {
				reader = new BufferedReader(new FileReader(path));
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
					Log log = this.logMapper.map(line);
					this.save(log);
					this.eventService.addLog(log);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
					reader.close();
					FileWriter writer = new FileWriter(path);
					writer.close();
					Thread.sleep(interval);	
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
