package com.epam.esm.converter;

import org.springframework.core.convert.converter.Converter;

import static com.epam.esm.dto.GiftCertificateQueryParametersDto.SortType;

/**
 * Class {@code SortTypeConverter} uses to convert request parameter to {@link SortType}.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class SortTypeConverter implements Converter<String, SortType> {

    @Override
    public SortType convert(String source) {
        try {
            return SortType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return SortType.NAME;
        }
    }
}
