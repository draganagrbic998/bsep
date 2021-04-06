package com.example.demo.service;

import com.example.demo.dto.ActivationDTO;
import com.example.demo.exception.ActivationExpiredException;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.UserDoesNotExistException;
import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

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
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(username);
	}

	public User save(User user) {
        if (user.getId() == null && this.userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }
        return this.userRepository.save(user);
    }

    public void delete(long id) {
        this.userRepository.deleteById(id);
    }

    public Page<User> findAll(Pageable pageable) {
	    return this.userRepository.findAll(pageable);
    }

    public List<Authority> getAuthorities() {
	    return this.authorityRepository.findAll();
    }

    public User getDisabled(String uuid) {
	    return this.userRepository.findByEnabledFalseAndActivationLink(uuid);
    }

    public User activate(ActivationDTO activationDTO) {
        User user = this.userRepository.findByEnabledFalseAndActivationLink(activationDTO.getUuid());

        if (user == null) {
            throw new UserDoesNotExistException();
        }

        if (user.getActivationExpiration().isBefore(Instant.now())) {
            throw new ActivationExpiredException();
        }

        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(activationDTO.getPassword()));
        return this.userRepository.save(user);
    }
    
    public User resetActivationLink(long id) {
	    User user = this.userRepository.findById(id).orElseThrow(UserDoesNotExistException::new);
	    user.setActivationExpiration(Instant.now().plus(48, ChronoUnit.HOURS));
	    user.setActivationLink(UUID.randomUUID().toString());
	    return this.userRepository.save(user);
    }
	
}
