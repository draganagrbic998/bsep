package com.example.demo.advice;

import com.example.demo.dto.ErrorDTO;
import com.example.demo.exception.AliasExistsException;
import com.example.demo.exception.CertificateAuthorityException;
import com.example.demo.exception.CertificateNotFoundException;
import com.example.demo.exception.InvalidIssuerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(CertificateNotFoundException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onCertificateNotFoundException(CertificateNotFoundException e){
        return new ResponseEntity<>(new ErrorDTO("The certificate was not found", "CERT_NOT_FOUND"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CertificateAuthorityException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onIssuerNotCAException(CertificateAuthorityException e){
        return new ResponseEntity<>(new ErrorDTO("The certificate is not a CA", "NOT_CA"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIssuerException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onIssuerNotValidException(InvalidIssuerException e){
        return new ResponseEntity<>(new ErrorDTO("The issuer is not valid", "INVALID_ISSUER"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AliasExistsException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onAliasAlreadyTakenException(AliasExistsException e){
        return new ResponseEntity<>(new ErrorDTO("The alias specified already exists", "ALIAS_EXISTS"),
                HttpStatus.BAD_REQUEST);
    }

}