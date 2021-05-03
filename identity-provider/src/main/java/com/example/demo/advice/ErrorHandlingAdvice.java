package com.example.demo.advice;

import com.example.demo.dto.ErrorDTO;
import com.example.demo.exception.AccountBlockedException;
import com.example.demo.exception.ActivationExpiredException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(ActivationExpiredException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onActivationExpiredException(ActivationExpiredException e) {
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage(), "ACTIVATION_EXPIRED"));
    }

    @ExceptionHandler(AccountBlockedException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onAccountBlockedException(AccountBlockedException exception){
        return ResponseEntity.badRequest().body(new ErrorDTO(exception.getMessage(), "TOO_MANY_ATTEMPTS"));

    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onAuthenticationException(AuthenticationException exception){
        return ResponseEntity.badRequest().body(new ErrorDTO("Auth exception", "AUTH_EXCEPTION"));

    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onBadCredentialsException(BadCredentialsException exception){
        return ResponseEntity.badRequest().body(new ErrorDTO("Bad credentials", "BAD_CREDENTIALS"));
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<Void> onException(Exception e){
    	e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}