package com.epam.esm.converter;

import org.springframework.core.convert.converter.Converter;

import static com.epam.esm.dto.GiftCertificateQueryParametersDto.OrderSort;

public class OrderSortConverter implements Converter<String, OrderSort> {

    @Override
    public OrderSort convert(String source) {
        try {
            return OrderSort.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            return OrderSort.ASC;
        }
    }
}
