package com.epam.esm.exception;

public enum  ExceptionKey {
    TAG_NOT_FOUND("tag.notFound"),
    TAG_ID_INCORRECT("tag.incorrectId"),
    TAG_NAME_INCORRECT("tag.incorrectName"),
    TAG_EXISTS("tag.exists"),
    GIFT_CERTIFICATE_NOT_FOUND("giftCertificate.notFound"),
    GIFT_CERTIFICATE_ID_INCORRECT("giftCertificate.incorrectId"),
    GIFT_CERTIFICATE_NAME_INCORRECT("giftCertificate.incorrectName"),
    GIFT_CERTIFICATE_DESCRIPTION_INCORRECT("giftCertificate.incorrectDescription"),
    GIFT_CERTIFICATE_PRICE_INCORRECT("giftCertificate.incorrectPrice"),
    GIFT_CERTIFICATE_DURATION_INCORRECT("giftCertificate.incorrectDuration"),
    GIFT_CERTIFICATE_DATES_INCORRECT("giftCertificate.incorrectDates"),
    INTERNAL_ERROR("internalError"),
    UNSUPPORTED_MEDIA_TYPE("unsupportedMediaType"),
    INCORRECT_PARAMETER("incorrectParameter");

    private String key;

    ExceptionKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
