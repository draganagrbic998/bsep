package com.example.demo.advice;

import com.example.demo.dto.ErrorDto;
import com.example.demo.exception.AliasExistsException;
import com.example.demo.exception.CertificateAuthorityException;
import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.exception.InvalidIssuerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(CertificateNotFoundException.class)
    @ResponseBody
    ResponseEntity<ErrorDto> onCertificateNotFoundException(CertificateNotFoundException e){
        return new ResponseEntity<>(new ErrorDto("The certificate was not found", "CERT_NOT_FOUND"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CertificateAuthorityException.class)
    @ResponseBody
    ResponseEntity<ErrorDto> onIssuerNotCAException(CertificateAuthorityException e){
        return new ResponseEntity<>(new ErrorDto("The certificate is not a CA", "NOT_CA"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIssuerException.class)
    @ResponseBody
    ResponseEntity<ErrorDto> onIssuerNotValidException(InvalidIssuerException e){
        return new ResponseEntity<>(new ErrorDto("The issuer is not valid", "INVALID_ISSUER"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AliasExistsException.class)
    @ResponseBody
    ResponseEntity<ErrorDto> onAliasAlreadyTakenException(AliasExistsException e){
        return new ResponseEntity<>(new ErrorDto("The alias specified already exists", "ALIAS_EXISTS"),
                HttpStatus.BAD_REQUEST);
    }

}