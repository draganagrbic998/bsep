package com.example.demo.controller;

import com.example.demo.dto.AuthTokenDTO;
import com.example.demo.dto.LoginDTO;
import com.example.demo.dto.TokenDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.UserDoesNotExistException;
import com.example.demo.model.User;
import com.example.demo.security.TokenUtils;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
	public ResponseEntity<Void> create(@Valid @RequestBody UserDTO userDTO) throws EmailAlreadyExistsException {
		return ResponseEntity.created(URI.create(this.userService.create(userDTO).getId().toString())).build();
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
		return ResponseEntity.ok(this.userService.read(pageable));
	}

}
