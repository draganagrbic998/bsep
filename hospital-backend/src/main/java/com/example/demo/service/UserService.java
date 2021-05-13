package com.example.demo.service;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.dto.AuthTokenDTO;
import com.example.demo.model.AlarmTriggering;
import com.example.demo.model.InvalidLogin;
import com.example.demo.model.User;
import com.example.demo.model.enums.AlarmRisk;
import com.example.demo.model.enums.LogStatus;
import com.example.demo.service.event.CommonEventService;
import com.example.demo.utils.AuthenticationProvider;
import com.example.demo.utils.Constants;
import com.example.demo.utils.Logger;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private final static String AUTH_API = Constants.IDENTITY_BACKEND + "/auth";

	private final AlarmTriggeringService alarmTriggeringService;
	private final CommonEventService commonEventService;
	private final RestTemplate restTemplate;
	private final AuthenticationProvider authProvider;
	private final Logger logger;

	@Override
	public User loadUserByUsername(String token) {
		try {
			return this.restTemplate.exchange(
					AUTH_API, 
					HttpMethod.POST, 
					this.authProvider.getAuthEntity(new TokenDTO(token)), 
					User.class).getBody();
		} 
		catch (Exception e) {
			return null;
		}
	}
	
	public AuthTokenDTO login(LoginDTO loginDTO) {
		try {
			return this.restTemplate.exchange(
					AUTH_API + "/login", 
					HttpMethod.POST, 
					this.authProvider.getAuthEntity(loginDTO), 
					AuthTokenDTO.class).getBody();
		}
		catch(Exception e) {
			this.commonEventService.addInvalidLogin(new InvalidLogin(this.authProvider.getIpAddress()));
			long days = this.days(loginDTO.getEmail());
			if (days >= 90)
				this.alarmTriggeringService.save(new AlarmTriggering(AlarmRisk.LOW, 
						String.format("Login attempt on account with %s email inactive for %d days!!", loginDTO.getEmail(), days)));
			this.logger.write(LogStatus.ERROR, "Unsuccessful login!");
			throw e;
		}
	}
		
	private long days(String email) {
		try {
			return this.restTemplate.exchange(
					AUTH_API + "/days/" + email, 
					HttpMethod.GET, 
					this.authProvider.getAuthEntity(null), 
					Long.class).getBody();
		}
		catch(Exception e) {
			return 0;
		}
	}

	public User currentUser() {
		try {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch(Exception e) {
			return null;
		}
	}
	
}
