package com.epam.esm.converter;

import org.springframework.core.convert.converter.Converter;

import static com.epam.esm.dto.GiftCertificateQueryParametersDto.SortOrder;

/**
 * Class {@code SortOrderConverter} uses to convert request parameter to {@link SortOrder}.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class SortOrderConverter implements Converter<String, SortOrder> {

    @Override
    public SortOrder convert(String source) {
        try {
            return SortOrder.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return SortOrder.ASC;
        }
    }
}
