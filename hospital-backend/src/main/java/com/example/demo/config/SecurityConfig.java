package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.security.AuthEntryPoint;
import com.example.demo.security.AuthFilter;
import com.example.demo.security.CertificateFilter;
import com.example.demo.security.RequestFilter;
import com.example.demo.service.AlarmTriggeringService;
import com.example.demo.service.CertificateService;
import com.example.demo.service.MaliciousIpAddressService;
import com.example.demo.service.UserService;
import com.example.demo.service.event.CommonEventService;
import com.example.demo.utils.AuthenticationProvider;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final UserService userService;
	private final CertificateService certificateService;
	private final AlarmTriggeringService alarmTriggeringService;
	private final MaliciousIpAddressService ipAddressService;
	private final CommonEventService commonEventService;
	private final AuthenticationProvider authProvider;

	@Bean
	public CorsFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin(Constants.FRONTEND);
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.exceptionHandling().authenticationEntryPoint(new AuthEntryPoint())
			.and().cors().and()
			.addFilterBefore(new AuthFilter(this.userService), BasicAuthenticationFilter.class)
			.addFilterBefore(new CertificateFilter(this.certificateService), BasicAuthenticationFilter.class)
			.addFilterBefore(new RequestFilter(this.alarmTriggeringService, this.ipAddressService, this.commonEventService, this.authProvider), BasicAuthenticationFilter.class)
			.csrf().disable();
        http
	        .headers()
	        .xssProtection()
	        .and()
	        .contentSecurityPolicy("script-src 'self'");
	}
		
}