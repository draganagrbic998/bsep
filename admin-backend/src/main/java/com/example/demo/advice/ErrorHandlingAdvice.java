package com.example.demo.advice;

import com.example.demo.dto.ErrorDTO;
import com.example.demo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(CertificateNotFoundException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onCertificateNotFoundException(){
        return new ResponseEntity<>(new ErrorDTO("The certificate was not found", "CERT_NOT_FOUND"),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CertificateAuthorityException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onIssuerNotCAException(){
        return new ResponseEntity<>(new ErrorDTO("The certificate is not a CA", "NOT_CA"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIssuerException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onIssuerNotValidException(){
        return new ResponseEntity<>(new ErrorDTO("The issuer is not valid", "INVALID_ISSUER"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AliasExistsException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onAliasAlreadyTakenException(){
        return new ResponseEntity<>(new ErrorDTO("The alias specified already exists", "ALIAS_EXISTS"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestTemplateVoidException.class)
    @ResponseBody
    ResponseEntity<Void> onRestTemplateVoidException(){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestTemplateMessageException.class)
    @ResponseBody
    ResponseEntity<ErrorDTO> onRestTemplateMessageException(RestTemplateMessageException e){
        return new ResponseEntity<>(e.getError(), HttpStatus.BAD_REQUEST);
    }

}