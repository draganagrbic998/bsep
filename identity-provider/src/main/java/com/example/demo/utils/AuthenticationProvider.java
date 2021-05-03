package com.example.demo.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider {

	@Autowired
	private HttpServletRequest request;
	
	public String getIpAddress() {
		return this.request.getHeader("X-Forward-For") != null ? this.request.getHeader("X-Forward-For") : this.request.getRemoteAddr();
	}
		
}
