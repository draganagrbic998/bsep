package com.example.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.ErrorDTO;
import com.example.demo.exception.InvalidCertificateException;
import com.example.demo.exception.RestTemplateMessageException;
import com.example.demo.exception.RestTemplateVoidException;

@ControllerAdvice
public class ErrorHandlingAdvice {

    @ExceptionHandler(InvalidCertificateException.class)
    @ResponseBody
    public ResponseEntity<ErrorDTO> onInvalidCertificateException(InvalidCertificateException e){
        return new ResponseEntity<>(new ErrorDTO(e.getMessage(), "CERT_INVALID"), HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Void> onNullPointerException(NullPointerException exception){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Void> handleException(Exception exception){
        exception.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
}
