package com.epam.esm.validator.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.validator.DtoValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.epam.esm.exception.ExceptionKey.*;

@Component
public class GiftCertificateValidator implements DtoValidator<GiftCertificateDto> {

    private static final int MIN_NUMBER = 1;
    private static final int MAX_DURATION = 365;
    private static final String NAME_PATTERN = "[\\p{L}0-9\\s-',]{1,100}";
    private static final String DESCRIPTION_PATTERN = "[\\p{L}0-9\\s-,._!?&'%:]{1,500}";
    private static final BigDecimal MIN_PRICE = BigDecimal.valueOf(1);
    private static final BigDecimal MAX_PRICE = BigDecimal.valueOf(999999.99);

    @Override
    public void validate(GiftCertificateDto giftCertificateDto) {
        validateName(giftCertificateDto.getName());
        validateDescription(giftCertificateDto.getDescription());
        validatePrice(giftCertificateDto.getPrice());
        validateDuration(giftCertificateDto.getDuration());
    }

    private void validateName(String name) {
        if (name == null || !name.trim().matches(NAME_PATTERN)) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_NAME_INCORRECT, name);
        }
    }

    private void validateDescription(String description) {
        if (description == null || !description.trim().matches(DESCRIPTION_PATTERN)) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_DESCRIPTION_INCORRECT, description);
        }
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_PRICE_INCORRECT);
        }
    }

    private void validateDuration(int duration) {
        if (duration < MIN_NUMBER || duration > MAX_DURATION) {
            throw new IncorrectParameterException(GIFT_CERTIFICATE_DURATION_INCORRECT, String.valueOf(duration));
        }
    }
}
