package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.configuration.PersistenceTestConfiguration;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = PersistenceTestConfiguration.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderDaoImplTest {
    private Page page;
    private User user;
    private GiftCertificate giftCertificate;
    private final OrderDao orderDao;

    @Autowired
    public OrderDaoImplTest(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @BeforeAll
    void setUp() {
        user = new User(1L, "shitikov.egor@gmail.com", "", "", "", new ArrayList<>());
        giftCertificate = new GiftCertificate(1L, "rest in hotel",
                "rest in good place", new BigDecimal("150.45"), 4,
                LocalDateTime.of(2021, 1, 1, 10, 0, 0),
                LocalDateTime.of(2021, 1, 2, 10, 0, 0),
                List.of(new Tag(1L, "rest"), new Tag(3L, "food")));
        page = new Page(5, 1);
    }

    @AfterAll
    void tearDown() {
        page = null;
        user = null;
        giftCertificate = null;
    }

    @Test
    void addCorrectDataShouldReturnOrder() {
        Order order = new Order();
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);
        order.setCost(new BigDecimal("150.45"));
        Order actual = orderDao.add(order);

        assertNotNull(actual);
    }

    @Test
    void findAllCorrectDataShouldReturnListOfOrders() {
        List<Order> users = orderDao.findAll(page);
        int actual = users.size();
        int expected = 5;

        assertEquals(expected, actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);
        order.setCost(new BigDecimal("150.45"));
        order.setPurchaseDate(LocalDateTime.of(2021, 2, 1, 10, 0, 0));
        Optional<Order> actualOptional = orderDao.findById(1L);
        Order actual = actualOptional.orElse(null);

        assertEquals(order, actual);
    }

    @Test
    void findByIdNotExistingOrderShouldReturnEmptyValue() {
        Optional<Order> actual = orderDao.findById(6);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void findOrdersByUserIdCorrectDataShouldReturnListOfOrders() {
        List<Order> actual = orderDao.findOrdersByUserId(1L, page);
        int expectedSize = 2;

        assertEquals(expectedSize, actual.size());
    }
}