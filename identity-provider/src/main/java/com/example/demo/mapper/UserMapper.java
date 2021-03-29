package com.example.demo.mapper;

import org.springframework.stereotype.Component;

import com.example.demo.model.User;

import com.example.demo.dto.UserDTO;

@Component
public class UserMapper {

	public UserDTO map(User user) {
		return new UserDTO(user);
	}
	
}
