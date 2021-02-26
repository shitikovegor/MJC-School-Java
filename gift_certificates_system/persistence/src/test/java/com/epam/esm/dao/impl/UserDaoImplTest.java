package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.configuration.PersistenceTestConfiguration;
import com.epam.esm.entity.User;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = PersistenceTestConfiguration.class)
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDaoImplTest {
    private final UserDao userDao;
    private Page page;

    @Autowired
    public UserDaoImplTest(UserDao userDao) {
        this.userDao = userDao;
    }

    @BeforeAll
    void setUp() {
        page = new Page(3, 1);
    }

    @AfterAll
    void tearDown() {
        page = null;
    }

    @Test
    void addCorrectDataShouldReturnUser() {
        User user = new User();
        user.setUsername("newUser@mail.ru");
        user.setFirstName("Name");
        user.setLastName("Lastname");
        User actual = userDao.add(user);

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
        User user = new User(3L, "name_surname@tut.by", "Oleg", "Petrov");
        Optional<User> actualOptional = userDao.findById(3);
        User actual = actualOptional.orElse(null);

        assertEquals(user, actual);
    }

    @Test
    void findByIdNotExistingUserShouldReturnEmptyValue() {
        Optional<User> actual = userDao.findById(6);
        assertEquals(Optional.empty(), actual);
    }

    @Test
    void findByEmailCorrectDataShouldReturnUser() {
        User user = new User(1L, "shitikov.egor@gmail.com", "Egor", "Shitikov");
        Optional<User> actualOptional = userDao.findByUsername("shitikov.egor@gmail.com");
        User actual = actualOptional.orElse(null);

        assertEquals(user, actual);
    }
}