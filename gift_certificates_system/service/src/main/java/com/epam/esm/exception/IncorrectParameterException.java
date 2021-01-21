package com.epam.esm.exception;

/**
 * Class {@code IncorrectParameterException} constructs a new runtime exception.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class IncorrectParameterException extends RuntimeException {
    private String messageParameter;
    private String messageKey;

    /**
     * Instantiates a new Incorrect parameter exception with specified message key
     * and parameter that is cause of exception.
     *
     * @param messageKey       the message key
     * @param messageParameter the message parameter
     */
    public IncorrectParameterException(String messageKey, String messageParameter) {
        this.messageKey = messageKey;
        this.messageParameter = messageParameter;
    }

    /**
     * Instantiates a new runtime exception with specified message key.
     *
     * @param messageKey the message key
     */
    public IncorrectParameterException(String messageKey) {
        this.messageKey = messageKey;
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
