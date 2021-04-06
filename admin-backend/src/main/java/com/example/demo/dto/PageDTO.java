package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class PageDTO<T> {
    private Collection<T> content;
    private long totalElements;
}
