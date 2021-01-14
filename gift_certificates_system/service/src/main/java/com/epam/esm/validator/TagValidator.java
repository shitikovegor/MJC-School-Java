package com.epam.esm.validator;

import com.epam.esm.exception.IncorrectParameterException;

import static com.epam.esm.exception.ExceptionKey.TAG_ID_INCORRECT;
import static com.epam.esm.exception.ExceptionKey.TAG_NAME_INCORRECT;

public class TagValidator {
    private static int MIN_NUMBER = 1;
    private static String NAME_PATTERN = "[\\p{L}0-9\\s-]{1,100}";

    public static void validateId(long id) {
        if (id < MIN_NUMBER) {
            throw new IncorrectParameterException(TAG_ID_INCORRECT.getKey(), String.valueOf(id));
        }
    }

    public static void validateName(String name) {
        if (name == null || !name.matches(NAME_PATTERN)) {
            throw new IncorrectParameterException(TAG_NAME_INCORRECT.getKey(), name);
        }
    }
}
