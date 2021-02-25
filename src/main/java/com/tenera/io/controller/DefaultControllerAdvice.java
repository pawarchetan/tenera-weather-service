package com.tenera.io.controller;

import com.tenera.io.dto.TeneraSearchError;
import com.tenera.io.exception.NoCityFoundException;
import com.tenera.io.exception.NoHistoryFoundException;
import com.tenera.io.exception.TeneraApplicationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Log4j2
public class DefaultControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHistoryFoundException.class)
    public ResponseEntity<TeneraSearchError> handleNoHistoryFoundException(NoHistoryFoundException ex) {
        log.error("Exception occurred : " + ex);
        TeneraSearchError teneraSearchError = new TeneraSearchError();
        teneraSearchError.setMessage(ex.getMessage());
        teneraSearchError.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(teneraSearchError, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoCityFoundException.class)
    public ResponseEntity<TeneraSearchError> handleNoCityFoundException(NoCityFoundException ex) {
        log.error("Exception occurred : " + ex);
        TeneraSearchError teneraSearchError = new TeneraSearchError();
        teneraSearchError.setMessage(ex.getMessage());
        teneraSearchError.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(teneraSearchError, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TeneraApplicationException.class)
    public ResponseEntity<TeneraSearchError> handleTeneraApplicationException(Exception ex) {
        log.error("Exception occurred : " + ex);
        TeneraSearchError teneraSearchError = new TeneraSearchError();
        teneraSearchError.setMessage(ex.getMessage());
        teneraSearchError.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(teneraSearchError, HttpStatus.NOT_FOUND);
    }

}
