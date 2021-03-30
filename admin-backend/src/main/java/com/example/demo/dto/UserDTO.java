package com.example.demo.dto;

import java.util.List;
import lombok.Data;

@Data
public class UserDTO {
	private String token;
	private List<String> authorities;
}
