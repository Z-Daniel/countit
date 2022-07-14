package com.zdaniel.countit.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalControllerAdvice {
    private static final Logger logger = Logger.getLogger(GlobalControllerAdvice.class.getName());

    @ExceptionHandler({ UniqueIdentifierAlreadyExistsException.class, EmptyDataException.class })
    public ResponseEntity<String> handleUniqueIdentifierAlreadyExistsException(Throwable ex) {
        logger.log(Level.WARNING, ex.getMessage());
        HttpHeaders httpHeaders = createHeadersFromThrowable(ex);
        return new ResponseEntity<>(ex.getMessage(), httpHeaders, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<String> handleResourceNotFoundException(Throwable ex) {
        logger.log(Level.WARNING, ex.getMessage());
        HttpHeaders httpHeaders = createHeadersFromThrowable(ex);
        return new ResponseEntity<>(ex.getMessage(), httpHeaders, HttpStatus.NOT_FOUND);
    }

    private HttpHeaders createHeadersFromThrowable(Throwable ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("error", ex.getMessage());
        return headers;
    }

}
