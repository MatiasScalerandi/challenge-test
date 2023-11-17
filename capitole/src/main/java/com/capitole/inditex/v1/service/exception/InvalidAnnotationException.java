package com.capitole.inditex.v1.service.exception;

import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import org.springframework.validation.Errors;

/**
 * Exception thrown when the request has errors
 *
 * @author Matias Scalerandi
 */
public class InvalidAnnotationException extends RuntimeException{

    private Errors errors;

    /**
     * Constructor.
     *
     * @param errors errors of the {@link ProductRetrievalRequest}
     */
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
