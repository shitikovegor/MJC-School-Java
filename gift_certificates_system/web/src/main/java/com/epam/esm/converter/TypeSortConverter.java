package com.epam.esm.converter;

import org.springframework.core.convert.converter.Converter;

import static com.epam.esm.dto.GiftCertificateQueryParametersDto.TypeSort;

public class TypeSortConverter implements Converter<String, TypeSort> {

    @Override
    public TypeSort convert(String source) {
        try {
            return TypeSort.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return TypeSort.NAME;
        }
    }
}
