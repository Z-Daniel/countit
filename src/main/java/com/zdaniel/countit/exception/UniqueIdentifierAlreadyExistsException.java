package com.zdaniel.countit.exception;

public class UniqueIdentifierAlreadyExistsException extends RuntimeException {

    public UniqueIdentifierAlreadyExistsException(String message) {
        super(message);
    }
}
