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

    private static Stream<TagDto> createCorrectData() {
        return Stream.of(
                new TagDto(1, "rest"),
                new TagDto(223, "2 and 1"),
                new TagDto(3093112, "экстримальный отдых"),
                new TagDto(7, "sea-cruise")
        );
    }

    private static Stream<TagDto> createIncorrectData() {
        return Stream.of(
                new TagDto(1, "rest & de@"),
                new TagDto(223, ""),
                new TagDto(-3093112, "экстримальный отдых"),
                new TagDto(0, "sea-cruise"),
                new TagDto(10, "sea, cruise")
        );
    }

    @ParameterizedTest
    @MethodSource("createCorrectData")
    void validateCorrectDataShouldNotThrowException(TagDto tagDto) {
        assertDoesNotThrow(() -> TagValidator.validate(tagDto));
    }

    @ParameterizedTest
    @MethodSource("createIncorrectData")
    void validateIncorrectDataShouldThrowException(TagDto tagDto) {
        assertThrows(IncorrectParameterException.class, () -> TagValidator.validate(tagDto));
    }

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