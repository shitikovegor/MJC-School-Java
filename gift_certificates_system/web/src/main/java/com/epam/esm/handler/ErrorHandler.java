package com.epam.esm.handler;

import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = IncorrectParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorInfo handleIncorrectParameterException(IncorrectParameterException exception) {
        return new ErrorInfo(exception.getMessage(), ErrorCode.INCORRECT_PARAMETER);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorInfo handleResourceNotFoundException(ResourceNotFoundException exception) {
        return new ErrorInfo(exception.getMessage(), ErrorCode.NOT_FOUND);
    }
}
