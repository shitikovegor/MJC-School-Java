package com.epam.esm.validator.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.DtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator implements DtoValidator<OrderDto> {

    private final UserService userService;
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public OrderValidator(UserService userService, GiftCertificateService giftCertificateService) {
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    public void validate(OrderDto orderDto) {
        if (orderDto.getUser() == null || orderDto.getGiftCertificate() == null) {
            throw new IncorrectParameterException(ExceptionKey.USER_NOT_COMPLETED);
        }
        userService.findById(orderDto.getUser().getId());
        giftCertificateService.findById(orderDto.getGiftCertificate().getId());
    }
}
