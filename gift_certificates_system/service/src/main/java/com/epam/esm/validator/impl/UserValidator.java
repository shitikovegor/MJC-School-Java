package com.epam.esm.validator.impl;

import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.validator.DtoValidator;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements DtoValidator<UserRegistrationDto> {

    private static final String EMAIL_PATTERN
            = "[A-Za-z0-9-]+([\\.|_][A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,100})";
    private static final String FIRST_LAST_NAMES_PATTERN = "^(?=.*[\\p{L}]$)[\\p{L}\\s-]{0,49}";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{6,20}$";

    @Override
    public void validate(UserRegistrationDto user) {
        validateUsername(user.getUsername());
        validateFirstName(user.getFirstName());
        validateLastName(user.getLastName());
        validatePassword(user.getPassword());
        if (!user.getPassword().matches(user.getConfirmPassword())) {
            throw new IncorrectParameterException(ExceptionKey.PASSWORDS_DO_NOT_MATCH);
        }
    }

    private void validateUsername(String name) {
        if (name == null || !name.trim().matches(EMAIL_PATTERN)) {
            throw new IncorrectParameterException(ExceptionKey.USER_EMAIL_INCORRECT, name);
        }
    }

    private void validateFirstName(String firstName) {
        if (firstName == null || !firstName.trim().matches(FIRST_LAST_NAMES_PATTERN)) {
            throw new IncorrectParameterException(ExceptionKey.USER_FIRSTNAME_INCORRECT, firstName);
        }
    }

    private void validateLastName(String lastName) {
        if (lastName == null || !lastName.trim().matches(FIRST_LAST_NAMES_PATTERN)) {
            throw new IncorrectParameterException(ExceptionKey.USER_LASTNAME_INCORRECT, lastName);
        }
    }

    private void validatePassword(String password) {
        if (password == null || !password.trim().matches(PASSWORD_PATTERN)) {
            throw new IncorrectParameterException(ExceptionKey.USER_PASSWORD_INCORRECT, password);
        }
    }
}
