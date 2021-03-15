package com.epam.esm.exception;

/**
 * Class {@code ErrorInfo} represents information about the error that occurred.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class ErrorInfo {

    /**
     * The Error message.
     */
    String errorMessage;

    /**
     * The Error code.
     */
    int errorCode;

    /**
     * Instantiates a new Error info.
     *
     * @param errorMessage the error message
     * @param errorCode    the error code
     */
    public ErrorInfo(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    /**
     * Gets error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets error message.
     *
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Sets error code.
     *
     * @param errorCode the error code
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
