package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.model.AlarmTriggering;
import com.example.demo.model.Request;
import com.example.demo.model.enums.AlarmRisk;
import com.example.demo.service.AlarmTriggeringService;
import com.example.demo.service.MaliciousIpAddressService;
import com.example.demo.service.event.CommonEventService;
import com.example.demo.utils.AuthenticationProvider;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestFilter extends OncePerRequestFilter {
	
	private final AlarmTriggeringService alarmTriggeringService;
	private final MaliciousIpAddressService ipAddressService;
	private final CommonEventService commonEventService;
	private final AuthenticationProvider authProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (request.getServletPath().equals("/auth/login")) {
			this.commonEventService.addRequest(new Request(true));
			if (this.ipAddressService.hasIpAddress(this.authProvider.getIpAddress()))
				this.alarmTriggeringService.save(new AlarmTriggering(AlarmRisk.MODERATE, 
						String.format("Login attempt from malicious %s IP address!!", this.authProvider.getIpAddress())));
		}
		else 
			this.commonEventService.addRequest(new Request(false));
		filterChain.doFilter(request, response);		

	}
	
}
