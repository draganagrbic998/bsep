package com.example.demo.service;

import com.example.demo.dto.ActivationDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.ActivationExpiredException;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.UserDoesNotExistException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
    private UserMapper userMapper;

	@Autowired
    private AuthorityRepository authorityRepository;

	public UserDTO create(UserDTO userDTO) throws EmailAlreadyExistsException {

        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new EmailAlreadyExistsException(userDTO.getEmail());
        }

        User user = userMapper.mapFromDTO(userDTO);

        List<Authority> authorities = authorityRepository.findAllById(userDTO.getAuthorities()
                .stream().map(Authority::getId).collect(Collectors.toList()));

        user.setAuthorities(new HashSet<>(authorities));

        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        user = userRepository.save(user);

        return userMapper.mapToDTO(user);
    }

    public UserDTO update(UserDTO userDTO) throws UserDoesNotExistException {
	    User user = userRepository.findById(userDTO.getId()).orElseThrow(UserDoesNotExistException::new);

	    user.setEmail(userDTO.getEmail());
	    user.setFirstName(userDTO.getFirstName());
	    user.setLastName(userDTO.getLastName());

        List<Authority> authorities = authorityRepository.findAllById(userDTO.getAuthorities()
                .stream().map(Authority::getId).collect(Collectors.toList()));

        user.setAuthorities(new HashSet<>(authorities));

	    user = userRepository.save(user);

	    return userMapper.mapToDTO(user);
    }

    public void delete(long id) {
	    if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
    }

    public Page<UserDTO> readAll(Pageable pageable) {
	    return userRepository.findAll(pageable).map(userMapper::mapToDTO);
    }

    public UserDTO resetActivationLink(long id) throws UserDoesNotExistException {
	    User u = this.userRepository.findById(id).orElseThrow(UserDoesNotExistException::new);
	    u.setActivationExpiration(Instant.now().plus(48, ChronoUnit.HOURS));
	    u.setActivationLink(UUID.randomUUID().toString());

	    u = this.userRepository.save(u);

	    return this.userMapper.mapToDTO(u);
    }

    public List<Authority> getAuthorities() {
	    return this.authorityRepository.findAll();
    }

    public UserDTO getDisabled(String uuid) {
	    return this.userMapper.mapToDTO(this.userRepository.findByEnabledFalseAndActivationLink(uuid));
    }

    public UserDTO activate(ActivationDTO activationDTO) throws UserDoesNotExistException, ActivationExpiredException {
        User found = this.userRepository.findByEnabledFalseAndActivationLink(activationDTO.getUuid());

        if (found == null) {
            throw new UserDoesNotExistException();
        }

        if (found.getActivationExpiration().isBefore(Instant.now())) {
            throw new ActivationExpiredException();
        }

        found.setEnabled(true);
        found.setPassword(passwordEncoder.encode(activationDTO.getPassword()));

        User saved = this.userRepository.save(found);

        return this.userMapper.mapToDTO(saved);
    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(username);
	}

	
}
