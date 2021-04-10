package com.example.demo.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider {

	@Autowired
	private HttpServletRequest request;
	
	public HttpEntity<Object> getAuthEntity(Object obj) {
		HttpHeaders headers = new HttpHeaders();
		headers.set(Constants.AUTHORIZATION_HEADER, this.request.getHeader(Constants.AUTHORIZATION_HEADER));
		return new HttpEntity<>(obj, headers);
	}
	
}
