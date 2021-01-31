package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.*;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final ModelMapper modelMapper;
    private final OrderDao orderDao;
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public OrderServiceImpl(ModelMapper modelMapper, OrderDao orderDao, UserService userService, GiftCertificateService giftCertificateService) {
        this.modelMapper = modelMapper;
        this.orderDao = orderDao;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    public OrderDto findById(long id) {
        Optional<Order> foundOrder = orderDao.findById(id);

        return foundOrder.map(order -> modelMapper.map(order, OrderDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.GIFT_CERTIFICATE_NOT_FOUND.getKey(),
                        String.valueOf(id)));
    }

    @Transactional
    @Override
    public long add(OrderDto orderDto) {
        orderDto.setGiftCertificate(giftCertificateService.findById(orderDto.getGiftCertificate().getId()));
        orderDto.setUser(userService.findById(orderDto.getUser().getId()));
        orderDto.setPurchaseDate(LocalDateTime.now());
        orderDto.setCost(orderDto.getGiftCertificate().getPrice());

        Order order = modelMapper.map(orderDto, Order.class);
        Order addedOrder = orderDao.add(order);

        return addedOrder.getId();
    }

    @Transactional
    @Override
    public void remove(long id) {
        Optional<Order> orderOptional = orderDao.findById(id);
        Order order = orderOptional.orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.TAG_NOT_FOUND.getKey(), String.valueOf(id)));
        orderDao.remove(order);
    }

    @Override
    public List<OrderDto> findByUserId(long id, PageDto pageDto) {
        Page page = modelMapper.map(pageDto, Page.class);
        return orderDao.findOrdersByUserId(id, page).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }
}
