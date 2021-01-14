package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.MessageFormat;
import java.util.Locale;

import static com.epam.esm.exception.ErrorCode.*;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(value = IncorrectParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorInfo handleIncorrectParameterException(IncorrectParameterException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(exception.getMessageKey(), new Object[]{}, locale),
                exception.getMessageParameter());
        return new ErrorInfo(errorMessage, INCORRECT_PARAMETER.getCode());
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorInfo handleResourceNotFoundException(ResourceNotFoundException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(exception.getMessageKey(), new Object[]{}, locale),
                exception.getMessageParameter());
        return new ErrorInfo(errorMessage, NOT_FOUND.getCode());
    }

    private String createErrorMessage(String message, String parameter) {
        return MessageFormat.format(message, parameter);
    }
}
