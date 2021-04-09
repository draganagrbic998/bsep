package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Authority;
import com.example.demo.service.CertificateService;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.security.cert.X509Certificate;
import java.util.List;

@RestController
@RequestMapping(value = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("permitAll()")
@AllArgsConstructor
public class UserController {

	private static final String CERTIFICATE_ATTRIBUTE = "javax.servlet.request.X509Certificate";

	private final CertificateService certificateService;
	private final UserService userService;
	private final UserMapper userMapper;

	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable, HttpServletRequest request) {
		if(!this.certificateService.validateCertificate(((X509Certificate[]) 
				request.getAttribute(CERTIFICATE_ATTRIBUTE))[0])) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(this.userService.findAll(pageable).map(UserDTO::new));
	}

	@GetMapping(value = "/authorities")
	public ResponseEntity<List<Authority>> getAuthorities(HttpServletRequest request) {
		if(!this.certificateService.validateCertificate(((X509Certificate[]) 
				request.getAttribute(CERTIFICATE_ATTRIBUTE))[0])) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(this.userService.getAuthorities());
	}

	@PostMapping
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
		if(!this.certificateService.validateCertificate(((X509Certificate[]) 
				request.getAttribute(CERTIFICATE_ATTRIBUTE))[0])) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(new UserDTO(this.userService.save(this.userMapper.map(userDTO))));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable long id, @Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
		if(!this.certificateService.validateCertificate(((X509Certificate[]) 
				request.getAttribute(CERTIFICATE_ATTRIBUTE))[0])) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(new UserDTO(this.userService.save(this.userMapper.map(id, userDTO))));
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<Void> delete(@PathVariable long id, HttpServletRequest request) {
		if(!this.certificateService.validateCertificate(((X509Certificate[]) 
				request.getAttribute(CERTIFICATE_ATTRIBUTE))[0])) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		this.userService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/send/{id}")
	public ResponseEntity<UserDTO> sendActivationMail(@PathVariable long id, HttpServletRequest request) {
		if(!this.certificateService.validateCertificate(((X509Certificate[]) 
				request.getAttribute(CERTIFICATE_ATTRIBUTE))[0])) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		return ResponseEntity.ok(new UserDTO(this.userService.resetActivationLink(id)));
	}

}
