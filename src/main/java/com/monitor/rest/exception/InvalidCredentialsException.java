package com.monitor.rest.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class InvalidCredentialsException extends RuntimeException {
    
    private final String errorMessage;
}
