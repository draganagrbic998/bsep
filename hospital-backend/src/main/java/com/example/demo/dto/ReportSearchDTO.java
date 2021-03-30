package com.example.demo.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportSearchDTO {

	private Date start;
	private Date end;
		
}
