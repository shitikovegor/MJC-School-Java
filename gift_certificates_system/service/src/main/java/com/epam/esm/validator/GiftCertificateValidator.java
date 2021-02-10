package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.IncorrectParameterException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.epam.esm.exception.ExceptionKey.*;

/**
 * Class {@code GiftCertificateValidator} uses to validate the gift-certificate
 * object and its fields.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class GiftCertificateValidator {
    private static int MIN_NUMBER = 1;
    private static int MAX_DURATION = 365;
    private static String NAME_PATTERN = "[\\p{L}0-9\\s-',]{1,100}";
    private static String DESCRIPTION_PATTERN = "[\\p{L}0-9\\s-,._!?&'%:]{1,500}";
    private static BigDecimal MIN_PRICE = new BigDecimal(1);
    private static BigDecimal MAX_PRICE = new BigDecimal(999999.99);

    /**
     * Validate object.
     *
     * @param giftCertificateDto the gift certificate dto
     */
    public static void validate(GiftCertificateDto giftCertificateDto) {
        validateName(giftCertificateDto.getName());
        validateDescription(giftCertificateDto.getDescription());
        validatePrice(giftCertificateDto.getPrice());
        validateDuration(giftCertificateDto.getDuration());
    }

    /**
     * Validate gift-certificate id.
     *
     * @param id the gift-certificate id
     */
    public static void validateId(long id) {
        if (id < MIN_NUMBER) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_ID_INCORRECT,
                    String.valueOf(id));
        }
    }

    /**
     * Validate gift-certificate name.
     *
     * @param name the gift-certificate name
     */
    public static void validateName(String name) {
        if (name == null || !name.trim().matches(NAME_PATTERN)) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_NAME_INCORRECT, name);
        }
    }

    /**
     * Validate gift-certificate description.
     *
     * @param description the gift-certificate description
     */
    public static void validateDescription(String description) {
        if (description == null || !description.trim().matches(DESCRIPTION_PATTERN)) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_DESCRIPTION_INCORRECT, description);
        }
    }

    /**
     * Validate gift-certificate price.
     *
     * @param price the gift-certificate price
     */
    public static void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(MIN_PRICE) == -1 || price.compareTo(MAX_PRICE) == 1) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_PRICE_INCORRECT, price.toString());
        }
    }

    /**
     * Validate gift-certificate duration.
     *
     * @param duration the gift-certificate duration
     */
    public static void validateDuration(int duration) {
        if (duration < MIN_NUMBER || duration > MAX_DURATION) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_DURATION_INCORRECT, String.valueOf(duration));
        }
    }
}
