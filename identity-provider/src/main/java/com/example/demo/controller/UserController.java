package com.example.demo.controller;

import com.example.demo.dto.RoleDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('READ_USERS')")
@AllArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
		return ResponseEntity.ok(this.userService.findAll(pageable).map(UserDTO::new));
	}

	@GetMapping("/roles")
	public ResponseEntity<List<RoleDTO>> findAllRoles() {
		return ResponseEntity.ok(this.userService.findAllRoles().stream().map(RoleDTO::new).collect(Collectors.toList()));
	}

	@PostMapping
	@PreAuthorize("hasAuthority('SAVE_USERS')")
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
		return ResponseEntity.ok(new UserDTO(this.userService.save(this.userMapper.map(userDTO))));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('SAVE_USERS')")
	public ResponseEntity<UserDTO> update(@PathVariable long id, @Valid @RequestBody UserDTO userDTO) {
		return ResponseEntity.ok(new UserDTO(this.userService.save(this.userMapper.map(id, userDTO))));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('DELETE_USERS')")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		this.userService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/send/{id}")
	public ResponseEntity<UserDTO> sendActivationMail(@PathVariable long id) {
		return ResponseEntity.ok(new UserDTO(this.userService.resetActivationLink(id)));
	}

}
