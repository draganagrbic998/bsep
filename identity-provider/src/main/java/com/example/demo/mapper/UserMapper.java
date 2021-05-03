package com.example.demo.mapper;

import com.example.demo.dto.RoleDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class UserMapper {
	
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	
	@Transactional(readOnly = true)
    public User map(UserDTO userDTO) {
    	User user = new User();
    	this.setModel(user, userDTO);
        return user;
    }
    
	@Transactional(readOnly = true)
    public User map(long id, UserDTO userDTO) {
    	User user = this.userRepository.findById(userDTO.getId()).get();
    	this.setModel(user, userDTO);
        return user;
    }
    
    private void setModel(User user, UserDTO userDTO) {
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        List<Role> roles = this.roleRepository.findAllById(userDTO.getRoles().stream().map(RoleDTO::getId).collect(Collectors.toList()));
        user.setRoles(new HashSet<>(roles));
    }

}
