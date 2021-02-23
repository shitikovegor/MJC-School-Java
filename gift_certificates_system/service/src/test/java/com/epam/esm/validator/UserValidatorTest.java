package com.epam.esm.validator;

import com.epam.esm.exception.IncorrectParameterException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserValidatorTest {


    @ParameterizedTest
    @ValueSource(longs = {1, 40, 454, 343223223})
    void validateCorrectIdShouldNotThrowException(long id) {
        assertDoesNotThrow(() -> UserValidator.validateId(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 0})
    void validateIncorrectIdShouldThrowException(long id) {
        assertThrows(IncorrectParameterException.class, () -> UserValidator.validateId(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"shitikov_egor@gmail.com", "sh.egor@tut.by", "user@mail.ru", "some_word@epam.com"})
    void validateCorrectUsernameShouldNotThrowException(String username) {
        assertDoesNotThrow(() -> UserValidator.validateUsername(username));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1user.mail.ru", "user.ru@mail.c", "uzd,s@mail.ru", "user@gmailcom"})
    void validateIncorrectNameShouldThrowException(String username) {
        assertThrows(IncorrectParameterException.class, () -> UserValidator.validateUsername(username));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Egor", "Anna-Maria", "Le", "A"})
    void validateCorrectFirstNameShouldNotThrowException(String firstname) {
        assertDoesNotThrow(() -> UserValidator.validateFirstName(firstname));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1yo", "Anna--", "Bjnjfsjdjkdfnkljnsdklfnsljdfnkjsdnflsjndfjsdlkjfnklsjdnflksd",
            "Egor."})
    void validateIncorrectFirstNameShouldThrowException(String firstname) {
        assertThrows(IncorrectParameterException.class, () -> UserValidator.validateUsername(firstname));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ivanov", "Ivanov-Petrov", "Le", "A"})
    void validateCorrectLastNameShouldNotThrowException(String lastname) {
        assertDoesNotThrow(() -> UserValidator.validateLastName(lastname));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "1yo", "Anna--", "Bjnjfsjdjkdfnkljnsdklfnsljdfnkjsdnflsjndfjsdlkjfnklsjdnflksd",
            "Egor."})
    void validateIncorrectLastNameShouldThrowException(String lastname) {
        assertThrows(IncorrectParameterException.class, () -> UserValidator.validateLastName(lastname));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Ivanov1", "Ujsdjsd12*1", "Us1@$!%*?&", "User123s&"})
    void validateCorrectPasswordShouldNotThrowException(String password) {
        assertDoesNotThrow(() -> UserValidator.validatePassword(password));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Us1", "1yosds", "password", "Bjnjfsjdjkdfn1kljnsdklf",
            "111111", "USER123"})
    void validateIncorrectPasswordShouldThrowException(String password) {
        assertThrows(IncorrectParameterException.class, () -> UserValidator.validatePassword(password));
    }
}