package com.epam.esm.exception;

/**
 * Class {@code ExceptionKey} represents key of exception
 * to generate an error message via message source .
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public final class   ExceptionKey {
    public static final String TAG_NOT_FOUND = "tag.notFound";
    public static final String TAG_ID_INCORRECT = "tag.incorrectId";
    public static final String TAG_NAME_INCORRECT = "tag.incorrectName";
    public static final String TAG_EXISTS = "tag.exists";
    public static final String TAG_DOES_NOT_EXIST = "tag.notExists";

    public static final String GIFT_CERTIFICATE_NOT_FOUND = "giftCertificate.notFound";
    public static final String GIFT_CERTIFICATE_ID_INCORRECT = "giftCertificate.incorrectId";
    public static final String GIFT_CERTIFICATE_NAME_INCORRECT = "giftCertificate.incorrectName";
    public static final String GIFT_CERTIFICATE_DESCRIPTION_INCORRECT = "giftCertificate.incorrectDescription";
    public static final String GIFT_CERTIFICATE_PRICE_INCORRECT = "giftCertificate.incorrectPrice";
    public static final String GIFT_CERTIFICATE_DURATION_INCORRECT = "giftCertificate.incorrectDuration";
    public static final String GIFT_CERTIFICATE_DATES_INCORRECT = "giftCertificate.incorrectDates";

    public static final String USER_NOT_FOUND = "user.notFound";
    public static final String USER_EXISTS = "user.exists";
    public static final String EMAIL_INCORRECT = "user.emailIncorrect";
    public static final String ORDER_NOT_FOUND = "order.notFound";

    public static final String INCORRECT_PAGE_NUMBER = "pagination.incorrectPageNumber";

    public static final String INTERNAL_ERROR = "internalError";
    public static final String UNSUPPORTED_MEDIA_TYPE = "unsupportedMediaType";
    public static final String INCORRECT_PARAMETER = "incorrectParameter";
    public static final String HANDLER_NOT_FOUND = "handlerNotFound";


    private ExceptionKey() {
    }
}
