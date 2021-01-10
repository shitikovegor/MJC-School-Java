package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ServiceException;

public class TagValidator {
    private static int MIN_NUMBER = 1;
    private static String NAME_PATTERN = "[\\p{L}0-9\\s-]{1,100}";

    public static void validate(TagDto tagDto) {
        validateId(tagDto.getId());
        validateName(tagDto.getName());
    }

    public static void validateId(long id) {
        if (id < MIN_NUMBER) {
            throw new ServiceException("Id is incorrect: " + id + ". Id should be a positive number.");
        }
    }

    public static void validateName(String name) {
        if (name == null || !name.matches(NAME_PATTERN)) {
            throw new ServiceException("Name is incorrect: " + name +
                    ". Name must contain letters, numbers, a hyphen. Size from 1 to 100.");
        }
    }
}
