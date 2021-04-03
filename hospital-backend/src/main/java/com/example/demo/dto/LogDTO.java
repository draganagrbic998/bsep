package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.Log;
import com.example.demo.model.LogMode;
import com.example.demo.model.LogStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogDTO {
	
	private long id;
	private Date date;
	private LogMode mode;
	private LogStatus status;
	private String ipAddress;
	private String description;
	
	public LogDTO(Log log) {
		super();
		this.id = log.getId();
		this.date = log.getDate();
		this.mode = log.getMode();
		this.status = log.getStatus();
		this.ipAddress = log.getIpAddress();
		this.description = log.getDescription();
	}
	
}
