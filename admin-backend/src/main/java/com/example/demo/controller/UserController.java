package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
public class UserController {


	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody UserDTO userDTO) {
		return ResponseEntity.created(URI.create(this.userService.create(userDTO).getId().toString())).build();
	}

	@PutMapping
	public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO) {
		return ResponseEntity.ok(this.userService.update(userDTO));
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		this.userService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> readAll() {
		return ResponseEntity.ok(this.userService.read());
	}

}
