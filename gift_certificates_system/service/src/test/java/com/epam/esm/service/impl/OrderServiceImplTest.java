package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.impl.OrderDaoImpl;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderServiceImplTest {
    private OrderService orderService;
    private UserService userService;
    private GiftCertificateService giftCertificateService;
    private OrderDao orderDao;
    private ModelMapper modelMapper;
    private Page page;
    private PageDto pageDto;
    private GiftCertificateDto giftCertificateDto;
    private GiftCertificate giftCertificate;
    private UserDto userDto;
    private User user;
    private Order order;
    private OrderDto orderDto;

    @BeforeAll
    void setUp() {
        orderDao = mock(OrderDaoImpl.class);
        userService = mock(UserServiceImpl.class);
        giftCertificateService = mock(GiftCertificateServiceImpl.class);
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        orderService = new OrderServiceImpl(modelMapper, orderDao, userService, giftCertificateService);
        page = new Page(5, 1, 10);
        pageDto = new PageDto(5, 1, 10);
    }

    @AfterAll
    void tearDown() {
        orderService = null;
        orderDao = null;
        userService = null;
        giftCertificateService = null;
        modelMapper = null;
        page = null;
        pageDto = null;
    }

    @BeforeEach
    void setUpMethod() {
        giftCertificateDto = new GiftCertificateDto(
                3, "dinner", "New Year",
                new BigDecimal(55), 2,
                LocalDateTime.of(2020, 12, 25, 23, 59, 0),
                LocalDateTime.of(2021, 12, 28, 23, 59, 59), new ArrayList<>());
        userDto = new UserDto(24L, "user@gmail.com", "", "", "", new ArrayList<>());
        giftCertificate = new GiftCertificate(
                3, "dinner", "New Year",
                new BigDecimal(55), 2,
                LocalDateTime.of(2020, 12, 25, 23, 59, 0),
                LocalDateTime.of(2021, 12, 28, 23, 59, 59), new ArrayList<>());
        user = new User(24L, "user@gmail.com", "", "", "", new ArrayList<>());

        order = new Order(15L, user, giftCertificate, new BigDecimal(55),
                LocalDateTime.of(2021, 1, 5, 23, 59, 0));
        orderDto = new OrderDto(15L, userDto, giftCertificateDto, new BigDecimal(55),
                LocalDateTime.of(2021, 1, 5, 23, 59, 0));
    }

    @AfterEach
    void tearDownMethod() {
        giftCertificateDto = null;
        giftCertificate = null;
        userDto = null;
        user = null;
        order = null;
        orderDto = null;
    }

    @Test
    void addCorrectDataShouldReturnOrderDtoId() {
        when(giftCertificateService.findById(anyLong())).thenReturn(giftCertificateDto);
        when(userService.findById(anyLong())).thenReturn(userDto);
        when(orderDao.add(order)).thenReturn(order);
        long expected = 15L;

        assertEquals(expected, orderService.add(orderDto));
    }

    @Test
    void addIncorrectDataShouldThrowException() {
        OrderDto orderDtoInvalid = new OrderDto(25L, new UserDto(), new GiftCertificateDto(), new BigDecimal(50),
                LocalDateTime.of(2021, 1, 5, 23, 59, 0));
        when(giftCertificateService.findById(anyLong())).thenReturn(null);
        when(userService.findById(anyLong())).thenReturn(null);
        when(orderDao.add(any(Order.class))).thenReturn(null);

        assertThrows(IncorrectParameterException.class, () -> orderService.add(orderDtoInvalid));
    }

    @Test
    void findByIdCorrectDataShouldReturnOrderDto() {
        when(orderDao.findById(15L)).thenReturn(Optional.of(order));
        long id = 15L;

        assertEquals(orderDto, orderService.findById(id));
    }

    @Test
    void findByIdCorrectDataShouldThrowException() {
        when(orderDao.findById(anyLong())).thenReturn(Optional.empty());
        long id = 2L;
        assertThrows(ResourceNotFoundException.class, () -> orderService.findById(id));
    }

    @Test
    void findByUserIdCorrectDataShouldReturnOrderDtoList() {
        List<OrderDto> expected = List.of(orderDto);
        when(orderDao.findTotalRecordsByUserId(15L)).thenReturn(10);
        when(orderDao.findOrdersByUserId(15L, page)).thenReturn(List.of(order));
        long id = 15L;

        assertEquals(expected, orderService.findByUserId(id, pageDto));
    }
}