package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Authority;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {
	
	private String token;
	private List<Authority.Authorities> authorities;
	
}
