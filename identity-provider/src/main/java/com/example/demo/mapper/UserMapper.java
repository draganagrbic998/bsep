package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
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
	private final AuthorityRepository authorityRepository;
	private final UserRepository userRepository;
	
    public User map(UserDTO userDTO) {
    	User user = new User();
    	if (userDTO.getPassword() != null) {
        	user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));        		
    	}
    	this.setModel(user, userDTO);
        return user;
    }
    
    public User map(long id, UserDTO userDTO) {
    	User user = this.userRepository.findById(userDTO.getId()).get();
    	this.setModel(user, userDTO);
        return user;
    }
    
    private void setModel(User user, UserDTO userDTO) {
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        List<Authority> authorities = this.authorityRepository.findAllById(userDTO.getAuthorities()
                .stream().map(Authority::getId).collect(Collectors.toList()));
        user.setAuthorities(new HashSet<>(authorities));
    }

}
