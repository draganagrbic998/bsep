package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.model.AlarmRisk;
import com.example.demo.model.AlarmTriggering;
import com.example.demo.model.Request;
import com.example.demo.service.AlarmTriggeringService;
import com.example.demo.service.CommonEventService;
import com.example.demo.service.MaliciousIpAddressService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestFilter extends OncePerRequestFilter {
	
	private final CommonEventService commonEventService;
	private final MaliciousIpAddressService ipAddressService;
	private final AlarmTriggeringService alarmTriggeringService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (request.getServletPath().equals("/auth/login")) {
			this.commonEventService.addRequest(new Request(true));
			String ipAddress = request.getHeader("X-Forward-For") != null ? request.getHeader("X-Forward-For") : request.getRemoteAddr();
			if (this.ipAddressService.hasIpAddress(ipAddress)) {
				this.alarmTriggeringService.save(new AlarmTriggering(AlarmRisk.MODERATE, "Login attempt from malicious " + ipAddress + " IP address!!"));
			}
		}
		else {
			this.commonEventService.addRequest(new Request(false));
		}
		filterChain.doFilter(request, response);		

	}
	
}
