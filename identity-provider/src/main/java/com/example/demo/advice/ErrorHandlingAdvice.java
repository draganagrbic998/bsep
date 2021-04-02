package com.example.demo.advice;

import com.example.demo.dto.ErrorDTO;
import com.example.demo.exception.ActivationExpiredException;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.UserDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), "EMAIL_EXISTS"),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserDoesNotExistException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onUserDoesNotExistException(UserDoesNotExistException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), "NO_USER"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ActivationExpiredException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onActivationExpiredException(ActivationExpiredException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), "ACTIVATION_EXPIRED"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleException(Exception exception){
        exception.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}