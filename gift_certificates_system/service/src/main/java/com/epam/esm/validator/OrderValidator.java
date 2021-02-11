package com.epam.esm.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;

public class OrderValidator {

    /**
     * Validate user.
     *
     * @param user the user
     */
    public static void validateUser(UserDto user) {
        if (user == null) {
            throw new IncorrectParameterException(ExceptionKey.USER_NOT_COMPLETED);
        }
        UserValidator.validateId(user.getId());
    }

    /**
     * Validate certificate.
     *
     * @param giftCertificate the gift certificate
     */
    public static void validateCertificate(GiftCertificateDto giftCertificate) {
        if (giftCertificate == null) {
            throw new IncorrectParameterException(ExceptionKey.GIFT_CERTIFICATE_NOT_COMPLETED);
        }
        GiftCertificateValidator.validateId(giftCertificate.getId());
    }
}
