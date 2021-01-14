package com.epam.esm.exception;

public class IncorrectParameterException extends RuntimeException {
    private String messageParameter;
    private String messageKey;

    public IncorrectParameterException(String messageKey, String messageParameter) {
        this.messageKey = messageKey;
        this.messageParameter = messageParameter;
    }

    public IncorrectParameterException(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageParameter() {
        return messageParameter;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
