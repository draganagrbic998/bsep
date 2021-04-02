package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.UserDoesNotExistException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
    private UserMapper userMapper;

	public UserDTO create(UserDTO userDTO) throws EmailAlreadyExistsException {

        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new EmailAlreadyExistsException(userDTO.getEmail());
        }

        User user = userMapper.mapFromDTO(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user = userRepository.save(user);

        return userMapper.mapToDTO(user);
    }

    public UserDTO update(UserDTO userDTO) throws UserDoesNotExistException {
	    User user = userRepository.findById(userDTO.getId()).orElseThrow(UserDoesNotExistException::new);

	    user.setEmail(userDTO.getEmail());
	    user.setFirstName(userDTO.getFirstName());
	    user.setLastName(userDTO.getLastName());
	    user.setAuthorities(userDTO.getAuthorities());

	    user = userRepository.save(user);

	    return userMapper.mapToDTO(user);
    }

    public void delete(long id) {
	    if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    public Page<UserDTO> read(Pageable pageable) {
	    return userRepository.findAll(pageable).map(userMapper::mapToDTO);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(username);
	}

	
}
