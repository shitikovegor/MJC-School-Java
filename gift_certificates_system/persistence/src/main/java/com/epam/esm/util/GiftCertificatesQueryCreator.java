package com.epam.esm.util;

import com.epam.esm.entity.GiftCertificateQueryParameters;

import java.text.MessageFormat;

public class GiftCertificatesQueryCreator {
    private static final String TAG_NAME_CONDITION = "tag.name LIKE ''{0}'' ";
    private static final String GIFT_CERTIFICATE_NAME_CONDITION = "gift_certificate.name LIKE ''%{0}%'' ";
    private static final String GIFT_CERTIFICATE_DESCRIPTION_CONDITION = "gift_certificate.description LIKE ''%{0}%'' ";
    private static final String SQL_WHERE = " WHERE ";
    private static final String SQL_AND = "AND ";
    private static final String SQL_GROUP_BY = "GROUP BY gift_certificate.id ";
    private static final String SQL_ORDER_BY = "ORDER BY ";

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
        if (parameters.getOrderSort() != null) {
            stringBuilder.append(SQL_ORDER_BY)
                    .append(parameters.getTypeSort().getQueryValue());
        }
        if (parameters.getTypeSort() != null) {
            stringBuilder.append(parameters.getOrderSort().getQueryValue());
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
