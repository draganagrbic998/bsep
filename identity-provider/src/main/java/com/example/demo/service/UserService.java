package com.example.demo.service;

import com.example.demo.dto.ActivationDTO;
import com.example.demo.exception.ActivationExpiredException;
import com.example.demo.exception.CommonlyUsedPasswordException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private final String COMMON_PASSWORDS_PATH = "src" + File.separatorChar + "main" + File.separatorChar + "resources"
			+ File.separatorChar + "common_passwords.txt";
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

	public User save(User user) {
		return this.userRepository.save(user);
	}

	public void delete(long id) {
		this.userRepository.deleteById(id);
	}

	public User resetActivationLink(long id) {
		User user = this.userRepository.findById(id).get();
		user.setActivationExpiration(Instant.now().plus(48, ChronoUnit.HOURS));
		user.setActivationLink(UUID.randomUUID().toString());
		return this.userRepository.save(user);
	}

	public User activate(ActivationDTO activationDTO) {
		User user = this.userRepository.findByEnabledFalseAndActivationLink(activationDTO.getUuid());

		if (user.getActivationExpiration().isBefore(Instant.now())) {
			throw new ActivationExpiredException();
		}

		Path path = Paths.get(this.COMMON_PASSWORDS_PATH);

		try {
			Files.lines(path).forEach(line -> {
				if (activationDTO.getPassword().equals(line))
					throw new CommonlyUsedPasswordException();
			});
		} catch (IOException ex) {
			System.out.format("I/O Exception:", ex);
		}

		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(activationDTO.getPassword()));
		return this.userRepository.save(user);
	}

	public User getDisabled(String uuid) {
		return this.userRepository.findByEnabledFalseAndActivationLink(uuid);
	}

}
