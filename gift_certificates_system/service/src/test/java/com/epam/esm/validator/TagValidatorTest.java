package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.validator.impl.TagValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagValidatorTest {

    private DtoValidator<TagDto> validator;

    @BeforeAll
    void setUp() {
        validator = new TagValidator();
    }

    @AfterAll
    void tearDown() {
        validator = null;
    }

    @ParameterizedTest
    @ValueSource(strings = {"Rest", "Отдых", "2 people", "some-word"})
    void validateCorrectNameShouldNotThrowException(String name) {
        TagDto actual = new TagDto();
        actual.setName(name);
        assertDoesNotThrow(() -> validator.validate(actual));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "rest!", "23$D", "rest, sea"})
    void validateIncorrectNameShouldThrowException(String name) {
        TagDto actual = new TagDto();
        actual.setName(name);
        assertThrows(IncorrectParameterException.class, () -> validator.validate(actual));
    }
}