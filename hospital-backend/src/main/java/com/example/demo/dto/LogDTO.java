package com.example.demo.dto;

import java.util.Date;

import com.example.demo.model.Log;
import com.example.demo.model.LogStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogDTO {
	
	private long id;
	private Date date;
	private LogStatus status;
	private String description;
	private String userName;
	private String computerName;
	private String serviceName;
	
	public LogDTO(Log log) {
		super();
		this.id = log.getId();
		this.date = log.getDate();
		this.status = log.getStatus();
		this.description = log.getDescription();
		this.userName = log.getUserName();
		this.computerName = log.getComputerName();
		this.serviceName = log.getServiceName();
	}
	
}
