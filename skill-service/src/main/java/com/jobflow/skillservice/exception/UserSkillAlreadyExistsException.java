package com.jobflow.skillservice.exception;

public class UserSkillAlreadyExistsException extends RuntimeException {

    public UserSkillAlreadyExistsException(String message) {
        super(message);
    }
}