package com.example.demo.service;

import com.example.demo.dto.ActivationDTO;
import com.example.demo.exception.ActivationExpiredException;
import com.example.demo.exception.CommonlyUsedPasswordException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Constants;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private static final String COMMON_PASSWORDS_PATH = Constants.RESOURCES_FOLDER + "common_passwords.txt";
	
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByEmail(username);
	}

	public Page<User> findAll(Pageable pageable) {
		return this.userRepository.findAll(pageable);
	}

	public List<Role> findAllRoles() {
		return this.roleRepository.findAll();
	}

	@Transactional(readOnly = false)
	public User save(User user) {
		return this.userRepository.save(user);
	}

	@Transactional(readOnly = false)
	public void delete(long id) {
		this.userRepository.deleteById(id);
	}

	@Transactional(readOnly = false)
	public User resetActivationLink(long id) {
		User user = this.userRepository.findById(id).get();
		user.setActivationExpiration(Instant.now().plus(48, ChronoUnit.HOURS));
		user.setActivationLink(UUID.randomUUID().toString());
		return this.userRepository.save(user);
	}

	@Transactional(readOnly = false)
	public User activate(ActivationDTO activationDTO) {
		User user = this.userRepository.findByEnabledFalseAndActivationLink(activationDTO.getUuid());

		if (user.getActivationExpiration().isBefore(Instant.now())) {
			throw new ActivationExpiredException();
		}

		try {
			Files.lines(Paths.get(COMMON_PASSWORDS_PATH)).forEach(line -> {
				if (activationDTO.getPassword().equals(line))
					throw new CommonlyUsedPasswordException();
			});
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(activationDTO.getPassword()));
		return this.userRepository.save(user);
	}

	public User getDisabled(String uuid) {
		return this.userRepository.findByEnabledFalseAndActivationLink(uuid);
	}

	public long days(String email) {
		User user = this.userRepository.findByEmail(email);
		if (user == null || user.isEnabled()) {
			return 0;
		}
		return Math.abs(ChronoUnit.DAYS.between(user.getCreatedDate(), Instant.now()));

	}

}
