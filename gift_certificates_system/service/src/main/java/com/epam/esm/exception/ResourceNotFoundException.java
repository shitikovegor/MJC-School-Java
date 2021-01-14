package com.epam.esm.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String messageParameter;
    private String messageKey;

    public ResourceNotFoundException(String messageKey, String messageParameter) {
        this.messageKey = messageKey;
        this.messageParameter = messageParameter;
    }

    public String getMessageParameter() {
        return messageParameter;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
