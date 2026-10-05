package com.jobflow.applicationtrackingservice.exception;

public class ApplicationAlreadyExistsException extends RuntimeException {

    public ApplicationAlreadyExistsException(String message) {
        super(message);
    }
}