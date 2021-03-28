package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LogDTO;
import com.example.demo.dto.LogSearchDTO;
import com.example.demo.mapper.LogMapper;
import com.example.demo.model.Log;
import com.example.demo.service.LogService;
import com.example.demo.utils.Constants;

@RestController
@RequestMapping(value = "/api/logs", produces = MediaType.APPLICATION_JSON_VALUE)
public class LogController {

	@Autowired
	private LogService logService;
	
	@Autowired
	private LogMapper logMapper;
	
	@PostMapping
	public ResponseEntity<List<LogDTO>> findAll(Pageable pageable, @Valid @RequestBody LogSearchDTO searchDTO, HttpServletResponse response){
		Page<Log> logs = this.logService.findAll(pageable, searchDTO);
		response.setHeader(Constants.ENABLE_HEADER, Constants.FIRST_PAGE + ", " + Constants.LAST_PAGE);
		response.setHeader(Constants.FIRST_PAGE, logs.isFirst() + "");
		response.setHeader(Constants.LAST_PAGE, logs.isLast() + "");
		return new ResponseEntity<>(this.logMapper.map(logs.toList()), HttpStatus.OK);
	}
	
}
