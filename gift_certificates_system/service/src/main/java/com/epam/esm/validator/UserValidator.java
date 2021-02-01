package com.epam.esm.validator;

import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;

public class UserValidator {
    private static final String EMAIL_PATTERN
            = "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,100})";

    /**
     * Validate user email.
     *
     * @param name the email
     */
    public static void validateEmail(String name) {
        if (name == null || !name.trim().matches(EMAIL_PATTERN)) {
            throw new IncorrectParameterException(ExceptionKey.EMAIL_INCORRECT, name);
        }
    }
}
