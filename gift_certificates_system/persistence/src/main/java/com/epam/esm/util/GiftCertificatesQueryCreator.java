package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificateQueryParameters;

import java.text.MessageFormat;

/**
 * Class {@code GiftCertificatesQueryCreator} represents
 * Gift-certificates query creator which uses parameters for search.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class GiftCertificatesQueryCreator {
    private static final String TAG_NAME_CONDITION = "tag.name LIKE ''{0}'' ";
    private static final String GIFT_CERTIFICATE_NAME_CONDITION = "gift_certificate.name LIKE ''%{0}%'' ";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_CONDITION = "gift_certificate.description LIKE ''%{0}%'' ";
    private static final String SQL_WHERE = " WHERE ";
    private static final String SQL_AND = "AND ";
    private static final String SQL_GROUP_BY = "GROUP BY gift_certificate.id ";
    private static final String SQL_ORDER_BY = "ORDER BY ";

    /**
     * Create query string.
     *
     * @param parameters the parameters
     * @return the string
     */
    public static String createQuery(GiftCertificateQueryParameters parameters) {
        StringBuilder stringBuilder = new StringBuilder();
        if (parameters.getTagName() != null) {
            addCondition(stringBuilder);
            stringBuilder.append(MessageFormat.format(TAG_NAME_CONDITION, parameters.getTagName()));
        }
        if (parameters.getName() != null) {
            addCondition(stringBuilder);
            stringBuilder.append(MessageFormat.format(GIFT_CERTIFICATE_NAME_CONDITION, parameters.getName()));
        }
        if (parameters.getDescription() != null) {
            addCondition(stringBuilder);
            stringBuilder.append(MessageFormat.format(GIFT_CERTIFICATE_DESCRIPTION_CONDITION, parameters.getDescription()));
        }
        stringBuilder.append(SQL_GROUP_BY);
        if (parameters.getSortOrder() != null) {
            stringBuilder.append(SQL_ORDER_BY)
                    .append(parameters.getSortType().getQueryValue());
        }
        if (parameters.getSortType() != null) {
            stringBuilder.append(parameters.getSortOrder().getQueryValue());
        }
        return stringBuilder.toString();

    }

    private static void addCondition(StringBuilder stringBuilder) {
        if (stringBuilder.toString().isEmpty()) {
            stringBuilder.append(SQL_WHERE);
        } else {
            stringBuilder.append(SQL_AND);
        }
    }
}
