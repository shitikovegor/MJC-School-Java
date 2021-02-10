package com.epam.esm.validator;

import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;

public class UserValidator {
    private static final String EMAIL_PATTERN
            = "[A-Za-z0-9-]+([\\.|_][A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,100})";
    private static int MIN_NUMBER = 1;

    /**
     * Validate user id.
     *
     * @param id the user id
     */
    public static void validateId(long id) {
        if (id < MIN_NUMBER) {
            throw new IncorrectParameterException(ExceptionKey.USER_ID_INCORRECT, String.valueOf(id));
        }
    }

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
