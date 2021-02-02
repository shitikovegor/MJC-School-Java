package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.IncorrectParameterException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GiftCertificateValidatorTest {

    private static Stream<GiftCertificateDto> createCorrectData() {
        GiftCertificateDto giftCertificateDto1 = new GiftCertificateDto(3, "dinner in cafe",
                "New Year dinner", new BigDecimal(50.99), 10,
                LocalDateTime.of(2020, 12, 31, 23, 59, 0),
                LocalDateTime.of(2021, 12, 31, 23, 59, 59), null);
        GiftCertificateDto giftCertificateDto2 = new GiftCertificateDto(56, "rest in hotel",
                "rest. In good place", new BigDecimal(150), 25,
                LocalDateTime.of(2020, 12, 25, 10, 0, 0),
                LocalDateTime.of(2021, 1, 12, 12, 59, 59), null);
        GiftCertificateDto giftCertificateDto3 = new GiftCertificateDto(13232, "Квадроциклы",
                "Отдых! Квадро & песок", new BigDecimal(34), 15,
                LocalDateTime.of(2021, 1, 10, 15, 0, 0),
                LocalDateTime.of(2021, 1, 10, 15, 0, 0), null);
        GiftCertificateDto giftCertificateDto4 = new GiftCertificateDto(3, "massage",
                "massage in Minsk-City", new BigDecimal(15), 12,
                LocalDateTime.of(2021, 1, 10, 15, 0, 0),
                LocalDateTime.of(2021, 1, 15, 15, 0, 0), null);

        return Stream.of(
                giftCertificateDto1,
                giftCertificateDto2,
                giftCertificateDto3,
                giftCertificateDto4
        );
    }

    private static Stream<GiftCertificateDto> createIncorrectData() {
        GiftCertificateDto incorrectName = new GiftCertificateDto(56, "rest in hotel!",
                "rest. In good place", new BigDecimal(150), 25,
                LocalDateTime.of(2020, 12, 25, 10, 0, 0),
                LocalDateTime.of(2021, 1, 12, 12, 59, 59), null);
        GiftCertificateDto incorrectDescription = new GiftCertificateDto(13232, "Квадроциклы",
                "", new BigDecimal(-34), 15,
                LocalDateTime.of(2021, 1, 10, 15, 0, 0),
                LocalDateTime.of(2021, 1, 10, 15, 0, 0), null);
        GiftCertificateDto incorrectPriceMin = new GiftCertificateDto(1323432, "Квадроциклы",
                "Активный отдых", new BigDecimal(-34), 15,
                LocalDateTime.of(2021, 1, 10, 15, 0, 0),
                LocalDateTime.of(2021, 1, 10, 15, 0, 0), null);
        GiftCertificateDto incorrectPriceMax = new GiftCertificateDto(1323432, "Квадроциклы",
                "Активный отдых", new BigDecimal(1000000), 15,
                LocalDateTime.of(2021, 1, 10, 15, 0, 0),
                LocalDateTime.of(2021, 1, 10, 15, 0, 0), null);
        GiftCertificateDto incorrectDurationMin = new GiftCertificateDto(3, "massage",
                "massage in Minsk-City", new BigDecimal(15), -35,
                LocalDateTime.of(2021, 1, 10, 15, 0, 0),
                LocalDateTime.of(2021, 1, 15, 15, 0, 0), null);
        GiftCertificateDto incorrectDurationMax = new GiftCertificateDto(3, "massage",
                "massage in Minsk-City", new BigDecimal(15), 400,
                LocalDateTime.of(2021, 1, 10, 15, 0, 0),
                LocalDateTime.of(2021, 1, 15, 15, 0, 0), null);

        return Stream.of(
                incorrectName,
                incorrectDescription,
                incorrectPriceMin,
                incorrectPriceMax,
                incorrectDurationMin,
                incorrectDurationMax
        );
    }

    private static Stream<BigDecimal> createCorrectPriceData() {
        return Stream.of(
                new BigDecimal(2),
                new BigDecimal(25.2),
                new BigDecimal(150000),
                new BigDecimal(99.99)
        );
    }

    private static Stream<BigDecimal> createIncorrectPriceData() {
        return Stream.of(new BigDecimal(-2),
                new BigDecimal(0),
                new BigDecimal(1200000));
    }

    @ParameterizedTest
    @MethodSource("createCorrectData")
    void validateCorrectDataShouldNotThrowException(GiftCertificateDto giftCertificateDto) {
        assertDoesNotThrow(() -> GiftCertificateValidator.validate(giftCertificateDto));
    }

    @ParameterizedTest
    @MethodSource("createIncorrectData")
    void validateIncorrectDataShouldThrowException(GiftCertificateDto giftCertificateDto) {
        assertThrows(IncorrectParameterException.class, () -> GiftCertificateValidator.validate(giftCertificateDto));
    }

    @ParameterizedTest
    @ValueSource(longs = {1L, 40L, 454L, 343223223L})
    void validateCorrectIdShouldNotThrowException(long id) {
        assertDoesNotThrow(() -> GiftCertificateValidator.validateId(id));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void validateIncorrectIdShouldThrowException(long id) {
        assertThrows(IncorrectParameterException.class, () -> GiftCertificateValidator.validateId(id));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Rest in Turkey", "Отдых в санатории", "2 people", "some-word"})
    void validateCorrectNameShouldNotThrowException(String name) {
        assertDoesNotThrow(() -> GiftCertificateValidator.validateName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "rest!", "23$D", "rest, sea"})
    void validateIncorrectNameShouldThrowException(String name) {
        assertThrows(IncorrectParameterException.class, () -> GiftCertificateValidator.validateName(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Rest in Turkey!", "Отдых в санатории, 4 дня", "2 people & dog", "some-word _or one?!"})
    void validateCorrectDescriptionShouldNotThrowException(String description) {
        assertDoesNotThrow(() -> GiftCertificateValidator.validateDescription(description));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "rest^", "<//s", ">Sea cruise", "90%"})
    void validateIncorrectDescriptionShouldThrowException(String description) {
        assertThrows(IncorrectParameterException.class, () -> GiftCertificateValidator.validateDescription(description));
    }

    @ParameterizedTest
    @MethodSource("createCorrectPriceData")
    void validateCorrectPriceShouldNotThrowException(BigDecimal price) {
        assertDoesNotThrow(() -> GiftCertificateValidator.validatePrice(price));
    }

    @ParameterizedTest
    @MethodSource("createIncorrectPriceData")
    void validateIncorrectPriceShouldThrowException(BigDecimal price) {
        assertThrows(IncorrectParameterException.class, () -> GiftCertificateValidator.validatePrice(price));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 23, 350})
    void validateCorrectDurationShouldNotThrowException(int duration) {
        assertDoesNotThrow(() -> GiftCertificateValidator.validateDuration(duration));
    }

    @ParameterizedTest
    @ValueSource(ints = {-45, 0, 400})
    void validateIncorrectDurationShouldThrowException(int duration) {
        assertThrows(IncorrectParameterException.class, () -> GiftCertificateValidator.validateDuration(duration));
    }
}