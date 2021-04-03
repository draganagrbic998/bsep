package com.example.demo.dto;

import com.example.demo.model.Authority;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AuthTokenDTO {
	private long id;
	private String token;
	private List<Authority.Authorities> authorities;
	
}
