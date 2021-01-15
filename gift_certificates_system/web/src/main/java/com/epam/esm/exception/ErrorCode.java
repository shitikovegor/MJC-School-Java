package com.epam.esm.exception;

public enum ErrorCode {
    INCORRECT_PARAMETER(4004),
    NOT_FOUND(4044),
    INTERNAL_ERROR(5004),
    UNSUPPORTED_MEDIA_TYPE(4154),
    BAD_REQUEST(4005);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
