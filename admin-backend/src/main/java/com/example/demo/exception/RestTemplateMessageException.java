package com.example.demo.exception;

import com.example.demo.dto.ErrorDTO;
import lombok.Getter;
import lombok.Setter;

public class RestTemplateMessageException extends Exception {

    @Getter
    @Setter
    private ErrorDTO error;

    public RestTemplateMessageException(ErrorDTO errorDTO) {
        super();
        this.error = errorDTO;
    }

}
