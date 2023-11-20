package com.capitole.inditex.v1.web.handler;

import com.capitole.inditex.v1.model.ErrorCode;
import com.capitole.inditex.v1.model.ErrorResponse;
import com.capitole.inditex.v1.service.exception.InvalidAnnotationException;
import com.capitole.inditex.v1.service.exception.PriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

/**
 * Exception handler that apply to all methods annoted with {@link RequestMapping @RequestMapping}
 *
 * @author Matias Scalerandi
 */
@ControllerAdvice
public class HandlerControllerAdvice {

    /**
     * Handles the {@link  PriceNotFoundException}
     *
     * @param e the caught exception
     * @return HTTP response entity consisting of {@link HttpStatus#NOT_FOUND} status code and body
     */
    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleStaticProfileNotFoundException(PriceNotFoundException e) {
        return new ErrorResponse()
                .withCode(ErrorCode.NOT_FOUND_PRICE.name())
                .withMessage(e.getMessage());
    }

    /**
     * Handles the {@link  ParseException}
     *
     * @param e the caught exception
     * @return HTTP response entity consisting of {@link HttpStatus#INTERNAL_SERVER_ERROR} status code and body
     */
    @ExceptionHandler(ParseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleStaticParseException(ParseException e) {
        return new ErrorResponse()
                .withCode(ErrorCode.ERROR_PARSING_DATE.name())
                .withMessage(e.getMessage());
    }

    /**
     * Handles the {@link  InvalidAnnotationException}
     *
     * @param e the caught exception
     * @return HTTP response entity consisting of {@link HttpStatus#BAD_REQUEST} status code and body
     */
    @ExceptionHandler(InvalidAnnotationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleStaticInvalidAnnotationException(InvalidAnnotationException e) {
        return new ErrorResponse()
                .withCode(ErrorCode.INVALID_ANNOTATION.name())
                .withMessage(e.getMessage());
    }

    /**
     * Handles the {@link  HttpMessageNotReadableException}
     *
     * @param e the caught exception
     * @return HTTP response entity consisting of {@link HttpStatus#BAD_REQUEST} status code and body
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ErrorResponse()
                .withCode(ErrorCode.ERROR_PARSING_DATE.name())
                .withMessage(e.getMessage());
    }

    /**
     * Handles the {@link  IllegalArgumentException}
     *
     * @param e the caught exception
     * @return HTTP response entity consisting of {@link HttpStatus#BAD_REQUEST} status code and body
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse()
                .withCode(ErrorCode.INVALID_ANNOTATION.name())
                .withMessage(e.getMessage());
    }
}
