package com.epam.esm.exception;

public enum ErrorCode {
    INCORRECT_PARAMETER(4004),
    NOT_FOUND(4044);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
