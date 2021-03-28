package com.example.demo.dto;

public class ReportDTO {

	private long infoLogs;
	private long warningLogs;
	private long errorLogs;
	private long fatalLogs;
	private long infoAlarms;
	private long warningAlarms;
	private long errorAlarms;
	private long fatalAlarms;
	private long descriptionAlarms;
	
	public ReportDTO() {
		super();
	}

	public ReportDTO(long infoLogs, long warningLogs, long errorLogs, long fatalLogs, 
			long infoAlarms, long warningAlarms, long errorAlarms, long fatalAlarms, long descriptionAlarms) {
		super();
		this.infoLogs = infoLogs;
		this.warningLogs = warningLogs;
		this.errorLogs = errorLogs;
		this.fatalLogs = fatalLogs;
		this.infoAlarms = infoAlarms;
		this.warningAlarms = warningAlarms;
		this.errorAlarms = errorAlarms;
		this.fatalAlarms = fatalAlarms;
		this.descriptionAlarms = descriptionAlarms;
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

	public long getInfoAlarms() {
		return infoAlarms;
	}

	public void setInfoAlarms(long infoAlarms) {
		this.infoAlarms = infoAlarms;
	}

	public long getWarningAlarms() {
		return warningAlarms;
	}

	public void setWarningAlarms(long warningAlarms) {
		this.warningAlarms = warningAlarms;
	}

	public long getErrorAlarms() {
		return errorAlarms;
	}

	public void setErrorAlarms(long errorAlarms) {
		this.errorAlarms = errorAlarms;
	}

	public long getFatalAlarms() {
		return fatalAlarms;
	}

	public void setFatalAlarms(long fatalAlarms) {
		this.fatalAlarms = fatalAlarms;
	}

	public long getDescriptionAlarms() {
		return descriptionAlarms;
	}

	public void setDescriptionAlarms(long descriptionAlarms) {
		this.descriptionAlarms = descriptionAlarms;
	}
	
}
