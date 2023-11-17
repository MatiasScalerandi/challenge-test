package com.capitole.inditex.v1.service.exception;

import org.springframework.validation.Errors;

public class InvalidAnnotationException extends RuntimeException{

    private Errors errors;
    public InvalidAnnotationException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }
}
