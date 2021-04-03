package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {

	private long successLogs;
	private long infoLogs;
	private long warningLogs;
	private long errorLogs;
	private long fatalLogs;
	private long alarms;
		
}
