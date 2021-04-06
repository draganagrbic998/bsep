package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserDoesNotExistException;
import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
	
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;
	private final AuthorityRepository authorityRepository;
	
    public User map(UserDTO userDTO) {
        User user;
        if (userDTO.getId() != null) {
        	user = this.userRepository.findById(userDTO.getId()).orElseThrow(UserDoesNotExistException::new);
        }
        else {
        	user = new User();
        	if (userDTO.getPassword() != null) {
            	user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));        		
        	}
        }
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        List<Authority> authorities = this.authorityRepository.findAllById(userDTO.getAuthorities()
                .stream().map(Authority::getId).collect(Collectors.toList()));
        user.setAuthorities(new HashSet<>(authorities));
        return user;
    }
    
}
