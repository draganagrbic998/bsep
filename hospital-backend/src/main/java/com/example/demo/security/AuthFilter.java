package com.example.demo.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthFilter extends OncePerRequestFilter {
	
	public final static String AUTHORIZATION_HEADER = "Authorization";

	private final UserDetailsService userService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = request.getHeader(AUTHORIZATION_HEADER);
		if (token != null && !token.isBlank()) {
			UserDetails user = this.userService.loadUserByUsername(token);
			if (user != null) {
				AuthToken authToken = new AuthToken(user, token);
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);	
		
	}
	
}
