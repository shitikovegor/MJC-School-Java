package com.epam.esm.exception;

/**
 * Enum {@code ErrorCode} represents custom codes of application errors.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public enum ErrorCode {
    INCORRECT_PARAMETER(4004),
    NOT_FOUND(4044),
    INTERNAL_ERROR(5004),
    UNSUPPORTED_MEDIA_TYPE(4154),
    BAD_REQUEST(4005),
    JWT_ERROR(4014),
    FORBIDDEN(4034);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public int getCode() {
        return code;
    }
}
