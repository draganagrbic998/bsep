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
    	return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage(), "CERT_NOT_FOUND"));
    }

    @ExceptionHandler(CertificateAuthorityException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onIssuerNotCAException(CertificateAuthorityException e){
    	return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage(), "CERT_NOT_CA"));
    }

    @ExceptionHandler(InvalidIssuerException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onIssuerNotValidException(InvalidIssuerException e){
    	return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage(), "ISSUER_INVALID"));
    }

    @ExceptionHandler(AliasTakenException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onAliasAlreadyTakenException(AliasTakenException e){
    	return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage(), "ALIAS_TAKEN"));
    }

    @ExceptionHandler(InvalidCertificateException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onInvalidCertificateException(InvalidCertificateException e){
    	return ResponseEntity.badRequest().body(new ErrorDTO(e.getMessage(), "CERT_INVALID"));
    }

    @ExceptionHandler(RestTemplateMessageException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onRestTemplateMessageException(RestTemplateMessageException e){
    	return ResponseEntity.badRequest().body(e.getError());
    }

    @ExceptionHandler(RestTemplateVoidException.class)
    @ResponseBody
    public ResponseEntity<Void> onRestTemplateVoidException(RestTemplateVoidException e){
    	return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<Void> onNullPointerException(NullPointerException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<Void> onException(Exception e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}