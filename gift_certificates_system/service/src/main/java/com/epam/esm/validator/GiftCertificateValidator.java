package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.epam.esm.exception.ExceptionKey.*;

public class GiftCertificateValidator {
    private static int MIN_NUMBER = 1;
    private static int MAX_DURATION = 365;
    private static String NAME_PATTERN = "[\\p{L}0-9\\s-]{1,100}";
    private static String DESCRIPTION_PATTERN = "[\\p{L}0-9\\s-,._!?&]{1,500}";
    private static BigDecimal MIN_PRICE = new BigDecimal(1);
    private static BigDecimal MAX_PRICE = new BigDecimal(999999.99);

    public static void validate(GiftCertificateDto giftCertificateDto) {
        validateName(giftCertificateDto.getName());
        validateDescription(giftCertificateDto.getDescription());
        validatePrice(giftCertificateDto.getPrice());
        validateDuration(giftCertificateDto.getDuration());
        validateDates(giftCertificateDto.getCreateDate(), giftCertificateDto.getLastUpdateDate());
    }

    public static void validateId(long id) {
        if (id < MIN_NUMBER) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_ID_INCORRECT.getKey(),
                    String.valueOf(id));
        }
    }

    public static void validateName(String name) {
        if (name == null || !name.trim().matches(NAME_PATTERN)) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_NAME_INCORRECT.getKey(), name);
        }
    }

    public static void validateDescription(String description) {
        if (description == null || !description.trim().matches(DESCRIPTION_PATTERN)) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_DESCRIPTION_INCORRECT.getKey(), description);
        }
    }

    public static void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(MIN_PRICE) == -1 || price.compareTo(MAX_PRICE) == 1) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_PRICE_INCORRECT.getKey(), price.toString());
        }
    }

    public static void validateDuration(int duration) {
        if (duration < MIN_NUMBER || duration > MAX_DURATION) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_DURATION_INCORRECT.getKey(), String.valueOf(duration));
        }
    }

    public static void validateDates(LocalDateTime createDate, LocalDateTime lastUpdateDate) {
        if (createDate == null || lastUpdateDate == null || createDate.isAfter(lastUpdateDate)) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_DATES_INCORRECT.getKey());
        }
    }
}
