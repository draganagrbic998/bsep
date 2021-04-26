package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.LoginDTO;
import com.example.demo.model.AlarmRisk;
import com.example.demo.model.AlarmTriggering;
import com.example.demo.model.InvalidLogin;
import com.example.demo.dto.AuthTokenDTO;
import com.example.demo.service.AlarmTriggeringService;
import com.example.demo.service.CommonEventService;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthController {

	private final UserService userService;
	private final CommonEventService commonEventService;
	private final AlarmTriggeringService alarmTriggeringService;
		
	@PostMapping(value = "/login")
	public ResponseEntity<AuthTokenDTO> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request){
		try {
			return ResponseEntity.ok(this.userService.login(loginDTO));
		}
		catch(Exception e) {
			String ipAddress = request.getHeader("X-Forward-For") != null ? request.getHeader("X-Forward-For") : request.getRemoteAddr();
			this.commonEventService.addInvalidLogin(new InvalidLogin(ipAddress));
			long days = this.userService.days(loginDTO.getEmail());
			if (days >= 90) {
				this.alarmTriggeringService.save(new AlarmTriggering("Login attempt on account inactive for " + days + " days!!", AlarmRisk.LOW));
			}
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
}
