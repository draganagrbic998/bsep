package com.example.demo.dto;

import javax.validation.constraints.NotNull;

public class LogSearchDTO {
	
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

	public LogSearchDTO() {
		super();
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
