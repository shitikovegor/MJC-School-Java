package com.epam.esm.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.MessageFormat;
import java.util.Locale;

import static com.epam.esm.exception.ErrorCode.*;

@RestControllerAdvice
public class ErrorHandler {

    private final MessageSource messageSource;

    @Autowired
    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(IncorrectParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleIncorrectParameterException(IncorrectParameterException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(exception.getMessageKey(), new Object[]{}, locale),
                exception.getMessageParameter());
        return new ErrorInfo(errorMessage, INCORRECT_PARAMETER.getCode());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo handleResourceNotFoundException(ResourceNotFoundException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(exception.getMessageKey(), new Object[]{}, locale),
                exception.getMessageParameter());
        return new ErrorInfo(errorMessage, NOT_FOUND.getCode());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorInfo handleUnsupportedTypeException(HttpMediaTypeNotSupportedException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(ExceptionKey.UNSUPPORTED_MEDIA_TYPE.getKey(), new Object[]{}, locale),
                exception.getMessage());
        return new ErrorInfo(errorMessage, UNSUPPORTED_MEDIA_TYPE.getCode());
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo handleRuntimeException(RuntimeException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(ExceptionKey.INTERNAL_ERROR.getKey(), new Object[]{}, locale),
                exception.getMessage());
        return new ErrorInfo(errorMessage, INTERNAL_ERROR.getCode());
    }

    private String createErrorMessage(String message, String parameter) {
        return MessageFormat.format(message, parameter);
    }
}
