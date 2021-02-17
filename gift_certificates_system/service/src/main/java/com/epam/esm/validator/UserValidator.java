package com.epam.esm.validator;

import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;

public class UserValidator {
    private static final String EMAIL_PATTERN
            = "[A-Za-z0-9-]+([\\.|_][A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,100})";
    private static final String FIRST_LAST_NAMES_PATTERN = "^(?=.*[\\p{L}]$)[\\p{L}\\s-]{0,49}";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,20}$";
    private static int MIN_NUMBER = 1;

    public static void validateUserRegistration(UserRegistrationDto user) {
        validateUsername(user.getUsername());
        validateFirstName(user.getFirstName());
        validateLastName(user.getLastName());
        validatePassword(user.getPassword());
        if (!user.getPassword().matches(user.getConfirmPassword())) {
            throw new IncorrectParameterException(ExceptionKey.PASSWORDS_DO_NOT_MATCH);
        }
    }



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
    public static void validateUsername(String name) {
        if (name == null || !name.trim().matches(EMAIL_PATTERN)) {
            throw new IncorrectParameterException(ExceptionKey.USER_EMAIL_INCORRECT, name);
        }
    }

    /**
     * Validate user first name.
     *
     * @param firstName the first name
     */
    public static void validateFirstName(String firstName) {
        if (firstName == null || !firstName.trim().matches(FIRST_LAST_NAMES_PATTERN)) {
            throw new IncorrectParameterException(ExceptionKey.USER_FIRSTNAME_INCORRECT, firstName);
        }
    }

    /**
     * Validate user last name.
     *
     * @param lastName the last name
     */
    public static void validateLastName(String lastName) {
        if (lastName == null || !lastName.trim().matches(FIRST_LAST_NAMES_PATTERN)) {
            throw new IncorrectParameterException(ExceptionKey.USER_LASTNAME_INCORRECT, lastName);
        }
    }

    /**
     * Validate user password.
     *
     * @param password the password
     */
    public static void validatePassword(String password) {
        if (password == null || !password.trim().matches(PASSWORD_PATTERN)) {
            throw new IncorrectParameterException(ExceptionKey.USER_PASSWORD_INCORRECT, password);
        }
    }
}
