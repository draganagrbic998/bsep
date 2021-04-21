package com.example.demo.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.ErrorDTO;
import com.example.demo.exception.RestTemplateMessageException;
import com.example.demo.exception.RestTemplateVoidException;

@ControllerAdvice
public class ErrorHandlingAdvice {

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
