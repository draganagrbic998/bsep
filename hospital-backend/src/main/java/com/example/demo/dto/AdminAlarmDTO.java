package com.example.demo.dto;

import com.example.demo.model.AdminAlarm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminAlarmDTO {

	private Long id;
	private boolean status;
	private String param;
	private long counts;
		
	public AdminAlarmDTO(AdminAlarm alarm) {
		super();
		this.id = alarm.getId();
		this.status = alarm.isStatus();
		this.param = alarm.getParam();
		this.counts = alarm.getCounts();
	}
	
}
