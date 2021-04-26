package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
	private long successLogs;
	private long infoLogs;
	private long warningLogs;
	private long errorLogs;
	private long fatalLogs;
	private long patientAlarms;
	private long logAlarms;
	private long dosAlarms;
	private long bruteForceAlarms;
}
