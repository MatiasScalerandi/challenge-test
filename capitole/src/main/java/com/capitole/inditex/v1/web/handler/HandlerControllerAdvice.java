package com.capitole.inditex.v1.web.handler;

import com.capitole.inditex.v1.model.ErrorCode;
import com.capitole.inditex.v1.model.ErrorResponse;
import com.capitole.inditex.v1.service.exception.InvalidAnnotationException;
import com.capitole.inditex.v1.service.exception.PriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@ControllerAdvice
public class HandlerControllerAdvice {

    @ExceptionHandler(PriceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleStaticProfileNotFoundException(PriceNotFoundException e){
        return new ErrorResponse()
                .withCode(ErrorCode.NOT_FOUND_PRICE.name())
                .withMessage(e.getMessage());
    }

    @ExceptionHandler(ParseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleStaticParseException(ParseException e){
        return new ErrorResponse()
                .withCode(ErrorCode.ERROR_PARSING_DATE.name())
                .withMessage(e.getMessage());
    }

    @ExceptionHandler(InvalidAnnotationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleStaticInvalidAnnotationException(InvalidAnnotationException e){
        return new ErrorResponse()
                .withCode(ErrorCode.INVALID_ANNOTATION.name())
                .withMessage(e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return new ErrorResponse()
                .withCode(ErrorCode.ERROR_PARSING_DATE.name())
                .withMessage(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e){
        return new ErrorResponse()
                .withCode(ErrorCode.ERROR_PARSING_DATE.name())
                .withMessage(e.getMessage());
    }

}
