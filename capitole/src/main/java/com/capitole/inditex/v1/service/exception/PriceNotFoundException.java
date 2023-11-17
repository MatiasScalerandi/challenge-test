package com.capitole.inditex.v1.service.exception;

import com.capitole.inditex.v1.entity.Price;

/**
 * Exception thrown when the {@link Price} is not found
 *
 * @author Matias Scalerandi
 */
public class PriceNotFoundException extends RuntimeException {
    /**
     * Constructor.
     *
     * @param message {@link String}
     */
    public PriceNotFoundException(String message) {
        super(message);
    }
}
