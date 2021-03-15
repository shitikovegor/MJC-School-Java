package com.epam.esm.validator;

import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.validator.impl.UserValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserValidatorTest {

    private DtoValidator<UserRegistrationDto> validator;

    private static Stream<UserRegistrationDto> createCorrectData() {
        UserRegistrationDto user1 = new UserRegistrationDto(1L, "shitikov_egor@gmail.com", "Ivanov1", "Ivanov1",
                "Egor", "Shitikov");
        UserRegistrationDto user2 = new UserRegistrationDto(1L, "sh.egor@tut.by", "Ujsdjsd121", "Ujsdjsd121",
                "Anna-Maria", "Ivanov-Petrov");
        UserRegistrationDto user3 = new UserRegistrationDto(1L, "user@mail.ru", "Password123", "Password123",
                "Le", "Le");
        UserRegistrationDto user4 = new UserRegistrationDto(1L, "some_word@epam.com", "User123s&", "User123s&",
                "A", "A");
        return Stream.of(
                user1,
                user2,
                user3,
                user4
        );
    }

    private static Stream<UserRegistrationDto> createIncorrectData() {
        UserRegistrationDto user1 = new UserRegistrationDto(1L, "1user.mail.ru", "Ivanov1", "Ivanov1",
                "Egor", "Shitikov");
        UserRegistrationDto user2 = new UserRegistrationDto(1L, "sh.egor@tut.by", "Ivanov1", "Ivanov2",
                "Egor", "Shitikov");
        UserRegistrationDto user3 = new UserRegistrationDto(1L, "user@mail.ru", "Ivanov1", "Ivanov1",
                "Anna--", "Shitikov");
        UserRegistrationDto user4 = new UserRegistrationDto(1L, "some_word@epam.com", "Ivanov1", "Ivanov1",
                "Egor", "Shitikov.");
        return Stream.of(
                user1,
                user2,
                user3,
                user4
        );
    }

    @BeforeAll
    void setUp() {
        validator = new UserValidator();
    }

    @AfterAll
    void tearDown() {
        validator = null;
    }

    @ParameterizedTest
    @MethodSource("createCorrectData")
    void validateCorrectDataShouldNotThrowException(UserRegistrationDto user) {
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @ParameterizedTest
    @MethodSource("createIncorrectData")
    void validateIncorrectDataShouldThrowException(UserRegistrationDto user) {
        assertThrows(IncorrectParameterException.class, () -> validator.validate(user));
    }
}