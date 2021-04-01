package com.example.demo.mapper;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User mapFromDTO(UserDTO userDTO) {
        User newUser = new User();
        newUser.setEmail(userDTO.getEmail());
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setAuthorities(userDTO.getAuthorities());

        return newUser;
    }

    public UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setAuthorities(user.getAuthorities());
        userDTO.setActivationLink(user.getActivationLink());
        userDTO.setActivationExpiration(user.getActivationExpiration());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEnabled(user.isEnabled());

        return userDTO;
    };
}
