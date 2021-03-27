package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.example.demo.dto.LogDTO;
import com.example.demo.mapper.LogMapper;
import com.example.demo.model.Log;
import com.example.demo.repository.LogRepository;
import com.example.demo.utils.Constants;
import com.google.gson.Gson;

@Service
@Transactional(readOnly = true)
public class LogService {

	@Autowired
	private LogRepository logRepository;
	
	@Autowired
	private LogMapper logMapper;
	
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
						catch(Exception e) {
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
	
	public Page<Log> findAll(Pageable pageable, LogDTO searchDTO) {
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
	
	private void readLogs(String path, long interval, String regExp) throws IOException, ParseException, InterruptedException {
		while (true) {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				this.save(this.logMapper.map(line));
			}
			reader.close();
			FileWriter writer = new FileWriter(path);
			writer.close();
			Thread.sleep(interval);
		}
	}
	
}
