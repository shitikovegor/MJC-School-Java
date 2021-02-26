package com.epam.esm.creation_util;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.OrderService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class OrderGenerator {
    private final OrderService orderService;

    @Autowired
    public OrderGenerator(OrderService orderService) {
        this.orderService = orderService;
    }

    public void addOrdersToDatabase(int count) {
        IntStream.range(0, count).forEach(i -> {
            OrderDto order = new OrderDto();
            UserDto user = new UserDto();
            user.setId(RandomUtils.nextLong(1, 1001));
            GiftCertificateDto giftCertificate = new GiftCertificateDto();
            giftCertificate.setId(RandomUtils.nextLong(1, 1001));
            order.setUser(user);
            order.setGiftCertificate(giftCertificate);
            orderService.add(order);
        });
    }
}
