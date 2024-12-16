package com.monitor.rest.validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.monitor.rest.exception.ObjectNotValidException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Component
public class ObjectsValidator {
    
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    
    private final Validator validator = validatorFactory.getValidator();

    public <T> void validate(T objectToValidate) throws ObjectNotValidException {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);
        Map<String, List<String>> errorResponse = new HashMap<>();
        if(!violations.isEmpty()){
            violations.forEach(violation -> {
                String property = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                errorResponse.put(property, Arrays.asList(message));
            });
            throw new ObjectNotValidException(errorResponse);
        }
    }
}
