package com.monitor.rest.exception;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ObjectNotValidException extends RuntimeException {
    
    private final Map<String, List<String>> errorMessages;
}
