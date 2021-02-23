package com.tenera.io.dto;

import lombok.Data;

@Data
public class TeneraSearchError {
    private int statusCode;
    private String message;
}
