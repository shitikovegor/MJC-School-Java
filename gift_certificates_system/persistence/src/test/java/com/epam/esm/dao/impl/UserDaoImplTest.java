package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.configuration.DaoTestConfiguration;
import com.epam.esm.entity.User;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DaoTestConfiguration.class)
@Transactional
class UserDaoImplTest {
    private static Page page;
    private static User user1;
    private static User user2;
    private static User user3;
    private UserDao userDao;

    @Autowired
    public UserDaoImplTest(UserDao userDao) {
        this.userDao = userDao;
    }

    @BeforeAll
    static void setUp() {
        user1 = new User();
        user1.setEmail("newUser@mail.ru");
        user2 = new User(3L, "name_surname@tut.by");
        user3 = new User(1L, "shitikov.egor@gmail.com");
        page = new Page(3, 1);
    }

    @AfterAll
    static void tearDown() {
        page = null;
        user1 = null;
        user2 = null;
        user3 = null;
    }

    @Test
    void addCorrectDataShouldReturnUser() {
        User actual = userDao.add(user1);

        assertNotNull(actual);
    }

    @Test
    void findAllCorrectDataShouldReturnListOfUsers() {
        List<User> users = userDao.findAll(page);
        int actual = users.size();
        int expected = 3;

        assertEquals(expected, actual);
    }

    @Test
    void findByIdCorrectDataShouldReturnUser() {
        Optional<User> actualOptional = userDao.findById(3);
        User actual = actualOptional.orElse(null);

        assertEquals(user2, actual);
    }

    @Test
    void findByIdNotExistingUserShouldReturnEmptyValue() {
        Optional<User> actual = userDao.findById(6);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void findByEmailCorrectDataShouldReturnUser() {
        Optional<User> actualOptional = userDao.findByEmail("shitikov.egor@gmail.com");
        User actual = actualOptional.orElse(null);

        assertEquals(user3, actual);
    }
}