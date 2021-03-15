package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import com.epam.esm.validator.DtoValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final ModelMapper modelMapper;
    private final OrderDao orderDao;
    private final UserService userService;
    private final GiftCertificateService giftCertificateService;
    private final DtoValidator<OrderDto> orderValidator;
    private final DtoValidator<PageDto> pageValidator;

    @Autowired
    public OrderServiceImpl(ModelMapper modelMapper, OrderDao orderDao, UserService userService, GiftCertificateService giftCertificateService, DtoValidator<OrderDto> orderValidator, DtoValidator<PageDto> pageValidator) {
        this.modelMapper = modelMapper;
        this.orderDao = orderDao;
        this.userService = userService;
        this.giftCertificateService = giftCertificateService;
        this.orderValidator = orderValidator;
        this.pageValidator = pageValidator;
    }

    @Override
    public OrderDto findById(long id) {
        Optional<Order> foundOrder = orderDao.findById(id);
        return foundOrder.map(order -> modelMapper.map(order, OrderDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.ORDER_NOT_FOUND,
                        String.valueOf(id)));
    }

    @Transactional
    @Override
    public long add(OrderDto orderDto) {
        orderValidator.validate(orderDto);
        orderDto.setGiftCertificate(giftCertificateService.findById(orderDto.getGiftCertificate().getId()));
        orderDto.setUser(userService.findById(orderDto.getUser().getId()));
        orderDto.setCost(orderDto.getGiftCertificate().getPrice());

        Order order = modelMapper.map(orderDto, Order.class);
        Order addedOrder = orderDao.add(order);

        return addedOrder.getId();
    }

    @Transactional
    @Override
    public void remove(long id) {
        OrderDto order = findById(id);
        orderDao.remove(modelMapper.map(order, Order.class));
    }

    @Override
    public List<OrderDto> findByUserId(long userId, PageDto pageDto) {
        pageValidator.validate(pageDto);
        Page page = modelMapper.map(pageDto, Page.class);
        return orderDao.findOrdersByUserId(userId, page).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public long findTotalRecordsByUserId(long userId) {
        return orderDao.findTotalRecordsByUserId(userId);
    }
}
