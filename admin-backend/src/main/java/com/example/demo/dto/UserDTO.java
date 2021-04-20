package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserDTO {
	
    private Long id;
    
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    
    private boolean enabled;
    private String activationLink;
    private Instant activationExpiration;
    
	@NotNull(message = "Roles cannot be null")
    private List<RoleDTO> roles;
    
}
