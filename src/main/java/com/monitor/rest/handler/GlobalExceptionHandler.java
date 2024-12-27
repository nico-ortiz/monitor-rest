package com.monitor.rest.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.monitor.rest.exception.DuplicateKeyException;
import com.monitor.rest.exception.ObjectNotValidException;
import com.monitor.rest.exception.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleObjectNotValidException(ObjectNotValidException exception) {
        return ResponseEntity
            .badRequest()
            .body(getErrorsMap(exception.getErrorMessages()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleObjectNotValidException(NotFoundException exception) {
        return ResponseEntity
            .status(404)
            .body(getErrorsMap(exception.getErrorMessage()));
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<?> handleObjectNotValidException(DuplicateKeyException exception) {
        return ResponseEntity
            .status(401)
            .body(getErrorsMap(exception.getErrorMessage()));
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<?> handleObjectNotValidException(JWTVerificationException exception) {
        return ResponseEntity
            .status(401)
            .body(getErrorsMap(exception.getMessage()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleObjectNotValidException(BadCredentialsException exception) {
        return ResponseEntity
            .status(401)
            .body(getErrorsMap(exception.getMessage()));
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleObjectNotValidException(IllegalArgumentException exception) {
        return ResponseEntity
            .status(401)
            .body(getErrorsMap(exception.getMessage()));
    }

    private Map<String, Object> getErrorsMap(Object errors) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }
}
