package com.epam.esm.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Locale;

import static com.epam.esm.exception.ErrorCode.*;

/**
 * Class {@code ErrorHandler} uses to handle exceptions in specific handler methods.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@RestControllerAdvice
public class ErrorHandler {

    private final MessageSource messageSource;

    /**
     * Instantiates a new Error handler.
     *
     * @param messageSource the message source
     */
    @Autowired
    public ErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handle {@link IncorrectParameterException}.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the error information
     */
    @ExceptionHandler(IncorrectParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleIncorrectParameterException(IncorrectParameterException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(exception.getMessageKey(), new Object[]{}, locale),
                exception.getMessageParameter());
        return new ErrorInfo(errorMessage, INCORRECT_PARAMETER.getCode());
    }

    /**
     * Handle {@link ResourceNotFoundException}.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the error information
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo handleResourceNotFoundException(ResourceNotFoundException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(exception.getMessageKey(), new Object[]{}, locale),
                exception.getMessageParameter());
        return new ErrorInfo(errorMessage, NOT_FOUND.getCode());
    }

    /**
     * Handle {@link HttpMediaTypeNotSupportedException}.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the error information
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorInfo handleUnsupportedTypeException(HttpMediaTypeNotSupportedException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(ExceptionKey.UNSUPPORTED_MEDIA_TYPE, new Object[]{}, locale),
                exception.getMessage());
        return new ErrorInfo(errorMessage, UNSUPPORTED_MEDIA_TYPE.getCode());
    }


    /**
     * Handle {@link HttpMessageNotReadableException}.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the error information
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfo handleMessageNotReadableException(HttpMessageNotReadableException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(ExceptionKey.INCORRECT_PARAMETER, new Object[]{}, locale),
                exception.getMessage());
        return new ErrorInfo(errorMessage, BAD_REQUEST.getCode());
    }

    /**
     * Handle {@link NoHandlerFoundException}.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the error information
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfo handleNoHandlerFoundException(NoHandlerFoundException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(ExceptionKey.HANDLER_NOT_FOUND, new Object[]{}, locale),
                exception.getMessage());
        return new ErrorInfo(errorMessage, NOT_FOUND.getCode());
    }

    /**
     * Handle {@link JwtException}.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the error information
     */
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorInfo handleExpiredJwtException(ExpiredJwtException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(ExceptionKey.JWT_ERROR, new Object[]{}, locale),
                exception.getMessage());
        return new ErrorInfo(errorMessage, JWT_ERROR.getCode());
    }

    /**
     * Handle {@link IllegalArgumentException}.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the error information
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo handleIllegalArgumentException(IllegalArgumentException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(ExceptionKey.JWT_ERROR, new Object[]{}, locale),
                exception.getMessage());
        return new ErrorInfo(errorMessage, JWT_ERROR.getCode());
    }

    /**
     * Handle {@link AccessDeniedException}.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the error information
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorInfo handleAccessDeniedException(AccessDeniedException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(ExceptionKey.JWT_ERROR, new Object[]{}, locale),
                exception.getMessage());
        return new ErrorInfo(errorMessage, FORBIDDEN.getCode());
    }

    /**
     * Handle {@link RuntimeException}.
     *
     * @param exception the exception
     * @param locale    the locale
     * @return the error information
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorInfo handleRuntimeException(RuntimeException exception, Locale locale) {
        String errorMessage = createErrorMessage(
                messageSource.getMessage(ExceptionKey.INTERNAL_ERROR, new Object[]{}, locale),
                exception.getMessage());
        return new ErrorInfo(errorMessage, INTERNAL_ERROR.getCode());
    }


    private String createErrorMessage(String message, String parameter) {
        return String.format(message, parameter);
    }
}
