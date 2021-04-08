package com.example.demo.exception;

import com.example.demo.dto.ErrorDTO;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
public class RestTemplateMessageException extends RuntimeException {

    @Getter
    @Setter
    private ErrorDTO error;

    public RestTemplateMessageException(ErrorDTO errorDTO) {
        super();
        this.error = errorDTO;
    }

}
