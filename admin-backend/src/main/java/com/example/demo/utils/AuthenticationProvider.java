package com.example.demo.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.example.demo.security.AuthFilter;

@Component
public class AuthenticationProvider {

	@Autowired
	private HttpServletRequest request;
	
	public String getIpAddress() {
		return this.request.getHeader("X-Forward-For") != null ? this.request.getHeader("X-Forward-For") : this.request.getRemoteAddr();
	}

	public HttpEntity<Object> getAuthEntity(Object obj) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(AuthFilter.AUTHORIZATION_HEADER, this.request.getHeader(AuthFilter.AUTHORIZATION_HEADER));
		return new HttpEntity<>(obj, headers);
	}
		
}
