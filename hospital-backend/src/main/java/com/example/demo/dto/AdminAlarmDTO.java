package com.example.demo.dto;

import javax.validation.constraints.NotBlank;

import com.example.demo.model.AdminAlarm;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminAlarmDTO {

	private Long id;
	private boolean status;
	private long counts;
	
	@NotBlank(message = "Param cannot be blank")
	private String param;
		
	public AdminAlarmDTO(AdminAlarm alarm) {
		super();
		this.id = alarm.getId();
		this.status = alarm.isStatus();
		this.counts = alarm.getCounts();
		this.param = alarm.getParam();
	}
	
}
