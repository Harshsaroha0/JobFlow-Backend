package com.jobflow.skillservice.exception;

public class SkillAlreadyExistsException extends RuntimeException {

    public SkillAlreadyExistsException(String message) {
        super(message);
    }
}