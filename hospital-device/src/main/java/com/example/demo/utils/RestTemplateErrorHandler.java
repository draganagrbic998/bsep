package com.example.demo.utils;

import com.example.demo.dto.ErrorDTO;
import com.example.demo.exception.RestTemplateMessageException;
import com.example.demo.exception.RestTemplateVoidException;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import lombok.SneakyThrows;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().series() == CLIENT_ERROR || httpResponse.getStatusCode().series() == SERVER_ERROR;
    }

    @Override
    @SneakyThrows
    public void handleError(ClientHttpResponse httpResponse) {
        Gson gson = new Gson();
        InputStream bodyStream = httpResponse.getBody();
        InputStreamReader bodyReader = new InputStreamReader(bodyStream);

        try {
            ErrorDTO errorDTO = gson.fromJson(bodyReader, ErrorDTO.class);
            throw new RestTemplateMessageException(errorDTO);
        } catch (JsonIOException | JsonSyntaxException ex) {
            throw new RestTemplateVoidException();
        }

    }
}
