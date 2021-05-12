package com.example.demo.service;

import com.example.demo.dto.ActivationDTO;
import com.example.demo.exception.ActivationExpiredException;
import com.example.demo.exception.CommonlyUsedPasswordException;
import com.example.demo.model.LogStatus;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.DatabaseCipher;
import com.example.demo.utils.Logger;

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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private final static String COMMON_PASSWORDS_PATH = "src" + File.separatorChar + "main" 
			+ File.separatorChar + "resources" + File.separatorChar + "common_passwords.txt";
	
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final Logger logger;
	private final DatabaseCipher databaseCipher;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.databaseCipher.decrypt(this.userRepository.findByEmail(username));
	}

	@Transactional(readOnly = true)
	public Page<User> findAll(Pageable pageable) {
		try {
			Page<User> response = this.userRepository.findAll(pageable);
			this.logger.write(LogStatus.SUCCESS, String.format("Users page number %d successfully fetched.", pageable.getPageNumber()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching users page number %d.", pageable.getPageNumber()));
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public List<Role> findAllRoles() {
		try {
			List<Role> response = this.roleRepository.findAll();
			this.logger.write(LogStatus.SUCCESS, "Roles successfully fetched.");
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while fetching roles.");
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public User save(User user) {
		try {
			User response = this.userRepository.save(user);
			this.logger.write(LogStatus.SUCCESS, String.format("User with id %d successfully saved.", response.getId()));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, "Error occured while saving user.");
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public void delete(long id) {
		try {
			this.userRepository.deleteById(id);
			this.logger.write(LogStatus.SUCCESS, String.format("User with id %d successfully deleted.", id));
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while deleting user with id %d.", id));
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public User resetActivationLink(long id) {
		try {
			User user = this.userRepository.findById(id).get();
			user.setActivationExpiration(Instant.now().plus(48, ChronoUnit.HOURS));
			user.setActivationLink(UUID.randomUUID().toString());
			user = this.userRepository.save(user);
			this.logger.write(LogStatus.SUCCESS, String.format("Activation link for user with id %d successfully reset.", id));
			return user;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while reseting activation link for user with id %d.", id));
			throw e;
		}
	}

	@Transactional(readOnly = false)
	public User activate(ActivationDTO activationDTO) {
		try {
			User user = this.userRepository.findByEnabledFalseAndActivationLink(activationDTO.getUuid());
			if (user.getActivationExpiration().isBefore(Instant.now()))
				throw new ActivationExpiredException();

			Files.lines(Paths.get(COMMON_PASSWORDS_PATH)).forEach(line -> {
				if (activationDTO.getPassword().equals(line))
					throw new CommonlyUsedPasswordException();
			});

			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(activationDTO.getPassword()));
			user = this.userRepository.save(user);
			this.logger.write(LogStatus.SUCCESS, String.format("User with uuid %s successfully activated.", activationDTO.getUuid()));
			return user;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while activating user with uuid %s.", activationDTO.getUuid()));
			throw new RuntimeException(e);
		}
	}

	@Transactional(readOnly = true)
	public User getDisabled(String uuid) {
		try {
			User response = this.userRepository.findByEnabledFalseAndActivationLink(uuid);
			this.logger.write(LogStatus.SUCCESS, String.format("Disabled user with uuid %s successfully fetched.", uuid));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching disabled user with uuid %s.", uuid));
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public long days(String email) {
		try {
			User user = this.userRepository.findByEmail(email);
			long response = user == null || user.isEnabled() ? 0 : Math.abs(ChronoUnit.DAYS.between(user.getCreatedDate(), Instant.now()));
			this.logger.write(LogStatus.SUCCESS, String.format("Inactive days for user with email %s successfully fetched.", email));
			return response;
		}
		catch(Exception e) {
			this.logger.write(LogStatus.ERROR, String.format("Error occured while fetching inactive days for user with email %s.", email));
			throw e;
		}
	}

}
