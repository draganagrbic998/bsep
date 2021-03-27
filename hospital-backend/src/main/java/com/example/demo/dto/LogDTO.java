package com.example.demo.dto;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.example.demo.model.Log;

public class LogDTO {

	private Date date;
	
	@NotNull
	private String status;
	
	@NotNull
	private String description;
	
	@NotNull
	private String userName;
	
	@NotNull
	private String computerName;
	
	@NotNull
	private String serviceName;
	
	public LogDTO() {
		super();
	}

	public LogDTO(Log log) {
		super();
		this.date = log.getDate();
		this.status = log.getStatus();
		this.description = log.getDescription();
		this.userName = log.getUserName();
		this.computerName = log.getComputerName();
		this.serviceName = log.getServiceName();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
}
