package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.IncorrectParameterException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GiftCertificateValidator {
    private static int MIN_NUMBER = 1;
    private static int MAX_DURATION = 365;
    private static String NAME_PATTERN = "[\\p{L}0-9\\s-]{1,100}";
    private static String DESCRIPTION_PATTERN = "[\\p{L}0-9\\s-,._!?&]{1,500}";
    private static BigDecimal MIN_PRICE = new BigDecimal(1);
    private static BigDecimal MAX_PRICE = new BigDecimal(999999.99);

    public static void validate(GiftCertificateDto giftCertificateDto) {
        validateId(giftCertificateDto.getId());
        validateName(giftCertificateDto.getName());
        validateDescription(giftCertificateDto.getDescription());
        validatePrice(giftCertificateDto.getPrice());
        validateDuration(giftCertificateDto.getDuration());
        validateDates(giftCertificateDto.getCreateDate(), giftCertificateDto.getLastUpdateDate());
    }

    public static void validateId(long id) {
        if (id < MIN_NUMBER) {
            throw new IncorrectParameterException("Id is incorrect: " + id + ". Id should be a positive number.");
        }
    }

    public static void validateName(String name) {
        if (name == null || !name.matches(NAME_PATTERN)) {
            throw new IncorrectParameterException("Name is incorrect: " + name +
                    ". Name must contain letters, numbers, a hyphen. Size from 1 to 100.");
        }
    }

    public static void validateDescription(String description) {
        if (description == null || !description.matches(DESCRIPTION_PATTERN)) {
            throw new IncorrectParameterException("Description is incorrect: " + description +
                    ". Description must contain letters, numbers, symbols: -,._!?&. Size from 1 to 500.");
        }
    }

    public static void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(MIN_PRICE) == -1 || price.compareTo(MAX_PRICE) == 1) {
            throw new IncorrectParameterException("Price is incorrect: " + price + ". Price should be from 1 to 999999,99.");
        }
    }

    public static void validateDuration(int duration) {
        if (duration < MIN_NUMBER || duration > MAX_DURATION) {
            throw new IncorrectParameterException("Duration is incorrect: " + duration + ". Duration should be from 1 to 365.");
        }
    }

    public static void validateDates(LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        if (createDate == null || lastUpdateDate == null || createDate.isAfter(lastUpdateDate)) {
            throw new IncorrectParameterException("Dates are incorrect. Create date should be before last update date.");
        }
    }
}
