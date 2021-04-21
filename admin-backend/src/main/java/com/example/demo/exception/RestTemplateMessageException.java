package com.example.demo.exception;

import com.example.demo.dto.ErrorDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@AllArgsConstructor
public class RestTemplateMessageException extends RuntimeException {

    private ErrorDTO error;

}
