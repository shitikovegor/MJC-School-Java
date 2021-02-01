package com.epam.esm.validator;

import com.epam.esm.exception.IncorrectParameterException;

import static com.epam.esm.exception.ExceptionKey.TAG_ID_INCORRECT;
import static com.epam.esm.exception.ExceptionKey.TAG_NAME_INCORRECT;

/**
 * Class {@code TagValidator} uses to validate fields of tag.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class TagValidator {
    private static int MIN_NUMBER = 1;
    private static String NAME_PATTERN = "[\\p{L}0-9\\s-]{1,100}";

    /**
     * Validate tag id.
     *
     * @param id the tag id
     */
    public static void validateId(long id) {
        if (id < MIN_NUMBER) {
            throw new IncorrectParameterException(TAG_ID_INCORRECT, String.valueOf(id));
        }
    }

    /**
     * Validate tag name.
     *
     * @param name the tag name
     */
    public static void validateName(String name) {
        if (name == null || !name.trim().matches(NAME_PATTERN)) {
            throw new IncorrectParameterException(TAG_NAME_INCORRECT, name);
        }
    }
}
