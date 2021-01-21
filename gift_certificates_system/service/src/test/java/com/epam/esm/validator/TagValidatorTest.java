package com.epam.esm.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.IncorrectParameterException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TagValidatorTest {

    @ParameterizedTest
    @ValueSource(longs = {1, 40, 454, 343223223})
    void validateCorrectIdShouldNotThrowException(long id) {
        assertDoesNotThrow(() -> TagValidator.validateId(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void validateIncorrectIdShouldThrowException(long id) {
        assertThrows(IncorrectParameterException.class, () -> TagValidator.validateId(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Rest", "Отдых", "2 people", "some-word"})
    void validateCorrectNameShouldNotThrowException(String name) {
        assertDoesNotThrow(() -> TagValidator.validateName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "rest!", "23$D", "rest, sea"})
    void validateIncorrectNameShouldThrowException(String name) {
        assertThrows(IncorrectParameterException.class, () -> TagValidator.validateName(name));
    }
}