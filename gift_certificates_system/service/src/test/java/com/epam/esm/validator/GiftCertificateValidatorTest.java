package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.validator.impl.GiftCertificateValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiftCertificateValidatorTest {

    private DtoValidator<GiftCertificateDto> validator;

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

    @BeforeAll
    void setUp() {
        validator = new GiftCertificateValidator();
    }

    @AfterAll
    void tearDown() {
        validator = null;
    }

    @ParameterizedTest
    @MethodSource("createCorrectData")
    void validateCorrectDataShouldNotThrowException(GiftCertificateDto giftCertificateDto) {
        assertDoesNotThrow(() -> validator.validate(giftCertificateDto));
    }

    @ParameterizedTest
    @MethodSource("createIncorrectData")
    void validateIncorrectDataShouldThrowException(GiftCertificateDto giftCertificateDto) {
        assertThrows(IncorrectParameterException.class, () -> validator.validate(giftCertificateDto));
    }
}