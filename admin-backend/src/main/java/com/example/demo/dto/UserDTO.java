package com.example.demo.dto;

import com.example.demo.model.Authority;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private String activationLink;
    private Instant activationExpiration;
    private Set<Authority> authorities;

}
