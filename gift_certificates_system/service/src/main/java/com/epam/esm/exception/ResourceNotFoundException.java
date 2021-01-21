package com.epam.esm.exception;

/**
 * Class {@code ResourceNotFoundException} constructs a new runtime exception.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {
    private String messageParameter;
    private String messageKey;

    /**
     * Instantiates a new Resource not found exception with specified message key
     * and parameter that is cause of exception.
     *
     * @param messageKey       the message key
     * @param messageParameter the message parameter
     */
    public ResourceNotFoundException(String messageKey, String messageParameter) {
        this.messageKey = messageKey;
        this.messageParameter = messageParameter;
    }

    /**
     * Gets message parameter of exception.
     *
     * @return the message parameter
     */
    public String getMessageParameter() {
        return messageParameter;
    }

    /**
     * Gets message key of exception.
     *
     * @return the message key
     */
    public String getMessageKey() {
        return messageKey;
    }
}
