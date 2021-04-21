package com.example.demo.service;

import com.example.demo.exception.AccountBlockedException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPT = 10;
    private final LoadingCache<String, Long> attemptsCache;
    private final UserRepository userRepository;

    @Autowired
    public LoginAttemptService(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
        this.attemptsCache = CacheBuilder.newBuilder().
                expireAfterWrite(30, TimeUnit.MINUTES).build(new CacheLoader<>() {
            public Long load(String key) {
                return 0L;
            }
        });
    }

    @EventListener
    public void loginSucceeded(AuthenticationSuccessEvent ev) {
        this.attemptsCache.invalidate(((User) ev.getAuthentication().getPrincipal()).getEmail());
    }

    @EventListener
    public void loginFailed(AuthenticationFailureBadCredentialsEvent ev) throws AccountBlockedException {
        String email = (String) ev.getAuthentication().getPrincipal();
        if (email.equalsIgnoreCase("superadmin@gmail.com")) {
            return;
        }

        long attempts;
        try {
            attempts = this.attemptsCache.get(email);
        } catch (ExecutionException e) {
            attempts = 0;
        }
        if (attempts++ < MAX_ATTEMPT) {
            this.attemptsCache.put(email, attempts);
            return;
        }

        User u = this.userRepository.findByEmail(email);
        this.attemptsCache.invalidate(email);
        if (u == null) return;
        u.setEnabled(false);
        this.userRepository.save(u);

        throw new AccountBlockedException();
    }
}
