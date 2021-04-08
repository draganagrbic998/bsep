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
    public ResponseEntity<ErrorDTO> onCertificateNotFoundException(CertificateNotFoundException e){
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), "CERT_NOT_FOUND"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CertificateAuthorityException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onIssuerNotCAException(CertificateAuthorityException e){
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), "NOT_CA"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidIssuerException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onIssuerNotValidException(InvalidIssuerException e){
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), "INVALID_ISSUER"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AliasExistsException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onAliasAlreadyTakenException(AliasExistsException e){
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), "ALIAS_TAKEN"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RestTemplateVoidException.class)
    @ResponseBody
    public ResponseEntity<Void> onRestTemplateVoidException(){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RestTemplateMessageException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onRestTemplateMessageException(RestTemplateMessageException e){
        return new ResponseEntity<>(e.getError(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Void> handleException(NullPointerException exception){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleException(Exception exception){
        exception.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}