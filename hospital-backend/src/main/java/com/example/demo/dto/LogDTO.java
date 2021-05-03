package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.Log;
import com.example.demo.model.enums.LogMode;
import com.example.demo.model.enums.LogStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
