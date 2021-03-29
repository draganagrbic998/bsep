package com.example.demo.dto;

import java.util.Date;

public class ReportSearchDTO {

	private Date start;
	private Date end;
	
	public ReportSearchDTO() {
		super();
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
}
