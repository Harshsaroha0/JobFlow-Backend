package com.jobflow.jobservice.exception;

public class UnauthorizedJobAccessException extends RuntimeException {

    public UnauthorizedJobAccessException(String message) {
        super(message);
    }
}