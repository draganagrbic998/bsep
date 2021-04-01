package com.example.demo.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

	private final UserDetailsService userService;

	public AuthFilter(UserDetailsService userService) {
		super();
		this.userService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader("Authorization");
		if (token != null) {
			UserDetails user = this.userService.loadUserByUsername(token);
			if (user != null) {
				AuthToken authToken = new AuthToken(user, token);
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
}