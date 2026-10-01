package com.jobflow.resumeservice.exception;


public class InvalidResumeFileException extends RuntimeException {

    public InvalidResumeFileException(String message) {
        super(message);
    }
}