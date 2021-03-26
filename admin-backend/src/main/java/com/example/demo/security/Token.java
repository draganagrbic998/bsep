package com.example.demo.security;

import com.example.demo.model.Admin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class Token extends AbstractAuthenticationToken {

    @Getter
    @Setter
    private String credentials; // TOKEN

    @Getter
    private final Admin principal;

    public Token(Admin user) {
        super(user.getAuthorities());
        this.principal = user;
    }
}
