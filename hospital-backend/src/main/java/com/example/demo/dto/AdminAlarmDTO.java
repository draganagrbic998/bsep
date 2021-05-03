package com.example.demo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.example.demo.model.AdminAlarm;
import com.example.demo.model.enums.LogStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminAlarmDTO {

	private Long id;
	
	@NotNull(message = "Status cannot be null")
	private LogStatus status;

	@Positive(message = "Counts must be positive integer")
	private long counts;
			
	public AdminAlarmDTO(AdminAlarm alarm) {
		super();
		this.id = alarm.getId();
		this.status = alarm.getStatus();
		this.counts = alarm.getCounts();
	}
	
}
