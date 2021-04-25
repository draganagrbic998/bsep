package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.model.Request;
import com.example.demo.service.CommonEventService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RequestFilter extends OncePerRequestFilter {
	
	private final CommonEventService commonEventService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if (request.getServletPath().equals("/auth/login")) {
			this.commonEventService.addRequest(new Request(true));
		}
		else {
			this.commonEventService.addRequest(new Request(false));
		}
		filterChain.doFilter(request, response);		

	}
	
}
