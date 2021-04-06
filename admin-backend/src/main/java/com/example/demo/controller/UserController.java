package com.example.demo.controller;

import com.example.demo.dto.PageDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.Authority;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
		try {
			return ResponseEntity.ok(this.userService.create(userDTO));
		} 
		catch (MessagingException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable long id, @Valid @RequestBody UserDTO userDTO) {
		userDTO.setId(id);
		return ResponseEntity.ok(this.userService.update(userDTO));
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		this.userService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<PageDTO<UserDTO>> findAll(Pageable pageable) {
		return ResponseEntity.ok(this.userService.findAll(pageable));
	}

	@GetMapping(value = "/authorities")
	public ResponseEntity<List<Authority>> getAuthorities() {
		return ResponseEntity.ok(this.userService.getAuthorities());
	}

	@PostMapping(value = "/send/{id}")
	public ResponseEntity<Void> sendActivationMail(@PathVariable long id) {
		try {
			this.userService.sendActivationMail(id);
			return ResponseEntity.ok().build();

		} 
		catch (MessagingException e) {
			return ResponseEntity.badRequest().build();
		}
	}

}
