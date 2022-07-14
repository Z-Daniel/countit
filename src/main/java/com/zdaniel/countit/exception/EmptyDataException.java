package com.zdaniel.countit.exception;

public class EmptyDataException extends IllegalArgumentException {

    public EmptyDataException() {
    }

    public EmptyDataException(String message) {
        super(message);
    }
}
