package com.jobflow.skillservice.exception;

public class UserSkillNotFoundException extends RuntimeException {

    public UserSkillNotFoundException(String message) {
        super(message);
    }
}