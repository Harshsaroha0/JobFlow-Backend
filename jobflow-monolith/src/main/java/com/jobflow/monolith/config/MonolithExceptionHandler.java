package com.jobflow.monolith.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class MonolithExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handle(Exception exception) {
        if (exception instanceof MethodArgumentNotValidException validation) {
            Map<String, String> fields = new LinkedHashMap<>();
            validation.getBindingResult().getFieldErrors().forEach(error ->
                fields.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(fields);
        }

        String name = exception.getClass().getSimpleName();
        HttpStatus status = switch (name) {
            case "UserNotFoundException", "JobNotFoundException", "ResumeNotFoundException",
                 "ApplicationNotFoundException", "SkillNotFoundException",
                 "UserSkillNotFoundException", "NotificationNotFoundException" -> HttpStatus.NOT_FOUND;
            case "EmailAlreadyExistsException", "ApplicationAlreadyExistsException",
                 "SkillAlreadyExistsException", "UserSkillAlreadyExistsException" -> HttpStatus.CONFLICT;
            case "UnauthorizedJobAccessException" -> HttpStatus.FORBIDDEN;
            case "InvalidResumeFileException" -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };

        Map<String, String> body = new LinkedHashMap<>();
        body.put("error", status.is5xxServerError() ? "Something went wrong" : exception.getMessage());
        return ResponseEntity.status(status).body(body);
    }
}