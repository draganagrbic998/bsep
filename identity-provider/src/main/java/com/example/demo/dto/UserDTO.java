package com.example.demo.dto;

import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank
    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean enabled;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String activationLink;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant activationExpiration;

    private Set<Authority> authorities;
    
    public UserDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.activationLink = user.getActivationLink();
        this.activationExpiration = user.getActivationExpiration();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.enabled = user.isEnabled();
        this.authorities = user.getAuthorities();
    }

}
