package com.example.demo.dto;

import com.example.demo.model.AdminAlarm;

public class AdminAlarmDTO {

	private Long id;
	private boolean status;
	private String param;
	private long counts;
	
	public AdminAlarmDTO() {
		super();
	}
	
	public AdminAlarmDTO(AdminAlarm alarm) {
		super();
		this.id = alarm.getId();
		this.status = alarm.isStatus();
		this.param = alarm.getParam();
		this.counts = alarm.getCounts();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public long getCounts() {
		return counts;
	}

	public void setCounts(long counts) {
		this.counts = counts;
	}
	
}
