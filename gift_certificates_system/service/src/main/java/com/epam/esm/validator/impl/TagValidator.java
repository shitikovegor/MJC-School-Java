package com.epam.esm.validator.impl;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.validator.DtoValidator;
import org.springframework.stereotype.Component;

import static com.epam.esm.exception.ExceptionKey.TAG_NAME_INCORRECT;

@Component
public class TagValidator implements DtoValidator<TagDto> {

    private static final String NAME_PATTERN = "[\\p{L}0-9\\s-]{1,100}";

    @Override
    public void validate(TagDto tagDto) {
        String name = tagDto.getName();
        if (name == null || !name.trim().matches(NAME_PATTERN)) {
            throw new IncorrectParameterException(TAG_NAME_INCORRECT, name);
        }
    }
}
