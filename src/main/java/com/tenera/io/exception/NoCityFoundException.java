package com.tenera.io.exception;

public class NoCityFoundException extends IllegalArgumentException {
    public NoCityFoundException(String s) {
        super(s);
    }
}
