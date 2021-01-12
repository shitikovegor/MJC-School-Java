package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.epam.esm.exception.ErrorCode.*;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IncorrectParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorInfo handleIncorrectParameterException(IncorrectParameterException exception) {
        return new ErrorInfo(exception.getMessage(), INCORRECT_PARAMETER.getCode());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorInfo handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorInfo(exception.getMessage(), NOT_FOUND.getCode());
    }
}
