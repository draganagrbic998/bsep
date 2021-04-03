package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import com.example.demo.model.AdminAlarm;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminAlarmDTO {

	private Long id;
	private boolean status;
	private long counts;
	
	@NotBlank(message = "Param can't be blank")
	private String param;
		
	public AdminAlarmDTO(AdminAlarm alarm) {
		super();
		this.id = alarm.getId();
		this.status = alarm.isStatus();
		this.counts = alarm.getCounts();
		this.param = alarm.getParam();
	}
	
}
