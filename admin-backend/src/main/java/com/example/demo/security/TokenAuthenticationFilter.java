package com.example.demo.security;

import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import com.example.demo.util.TokenUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final AdminService adminService;

    public TokenAuthenticationFilter(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String username;
        String authToken = TokenUtils.getToken(httpServletRequest);
        if (authToken != null) {
            username = TokenUtils.getUsernameFromToken(authToken);

            if (username != null) {
                try{
                    Admin admin = adminService.loadUserByUsername(username);
                    if (TokenUtils.validateToken(authToken, admin)) {
                        // kreiraj autentifikaciju
                        Token authentication = new Token(admin);
                        authentication.setCredentials(authToken);
                        authentication.setAuthenticated(true);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }catch (UsernameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}