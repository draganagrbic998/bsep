package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.UserDoesNotExistException;
import com.example.demo.model.Authority;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) throws EmailAlreadyExistsException {
		UserDTO created = this.userService.create(userDTO);
		return ResponseEntity.created(URI.create(created.getId().toString())).body(created);
	}

	@PutMapping
	public ResponseEntity<UserDTO> update(@Valid @RequestBody UserDTO userDTO) throws UserDoesNotExistException {
		return ResponseEntity.ok(this.userService.update(userDTO));
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		this.userService.delete(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<Page<UserDTO>> readAll(Pageable pageable) {
		return ResponseEntity.ok(this.userService.readAll(pageable));
	}

	@GetMapping(value = "/send/{id}")
	public ResponseEntity<UserDTO> sendActivationMail(@PathVariable long id) throws UserDoesNotExistException {
		return ResponseEntity.ok(this.userService.resetActivationLink(id));
	}

	@GetMapping(value = "/authorities")
	public ResponseEntity<List<Authority>> getAuthorities() {
		return ResponseEntity.ok(this.userService.getAuthorities());
	}

}
