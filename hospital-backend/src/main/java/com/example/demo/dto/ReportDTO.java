package com.example.demo.dto;

public class ReportDTO {

	private long infoLogs;
	private long warningLogs;
	private long errorLogs;
	private long fatalLogs;
	private long alarms;
	
	public ReportDTO() {
		super();
	}
	
	public ReportDTO(long infoLogs, long warningLogs, long errorLogs, long fatalLogs, long alarms) {
		super();
		this.infoLogs = infoLogs;
		this.warningLogs = warningLogs;
		this.errorLogs = errorLogs;
		this.fatalLogs = fatalLogs;
		this.alarms = alarms;
	}

	public long getInfoLogs() {
		return infoLogs;
	}

	public void setInfoLogs(long infoLogs) {
		this.infoLogs = infoLogs;
	}

	public long getWarningLogs() {
		return warningLogs;
	}

	public void setWarningLogs(long warningLogs) {
		this.warningLogs = warningLogs;
	}

	public long getErrorLogs() {
		return errorLogs;
	}

	public void setErrorLogs(long errorLogs) {
		this.errorLogs = errorLogs;
	}

	public long getFatalLogs() {
		return fatalLogs;
	}

	public void setFatalLogs(long fatalLogs) {
		this.fatalLogs = fatalLogs;
	}

	public long getAlarms() {
		return alarms;
	}

	public void setAlarms(long alarms) {
		this.alarms = alarms;
	}
	
}
