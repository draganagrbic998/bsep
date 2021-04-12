package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogConfiguration {
    private String path;
    private long interval;
    private String regExp;
}

