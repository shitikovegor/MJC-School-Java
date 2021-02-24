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
import com.epam.esm.validator.OrderValidator;
import com.epam.esm.validator.PageValidator;
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
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.ORDER_NOT_FOUND,
                        String.valueOf(id)));
    }

    @Transactional
    @Override
    public long add(OrderDto orderDto) {
        OrderValidator.validateUser(orderDto.getUser());
        OrderValidator.validateCertificate(orderDto.getGiftCertificate());
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
        Optional<Order> orderOptional = orderDao.findById(id);
        Order order = orderOptional.orElseThrow(() ->
                new ResourceNotFoundException(ExceptionKey.ORDER_NOT_FOUND, String.valueOf(id)));
        orderDao.remove(order);
    }

    @Override
    public List<OrderDto> findByUserId(long userId, PageDto pageDto) {
        int totalRecords = orderDao.findTotalRecordsByUserId(userId);
        if (totalRecords == 0) {
            throw new ResourceNotFoundException(ExceptionKey.USER_ORDERS_NOT_FOUND, String.valueOf(userId));
        }
        pageDto.setTotalRecords(totalRecords);
        PageValidator.validatePage(pageDto);
        Page page = modelMapper.map(pageDto, Page.class);
        return orderDao.findOrdersByUserId(userId, page).stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }
}
