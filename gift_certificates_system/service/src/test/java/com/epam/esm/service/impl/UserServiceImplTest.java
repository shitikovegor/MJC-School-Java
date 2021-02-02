package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

class UserServiceImplTest {
    private static UserService userService;
    private static UserDao userDao;
    private static ModelMapper modelMapper;
    private static Page page;
    private static PageDto pageDto;
    private static User user1;
    private static User user2;
    private static User user3;
    private static UserDto userDto1;
    private static UserDto userDto2;
    private static UserDto userDto3;
    private static UserDto userDtoInvalid;

    @BeforeAll
    static void setUp() {
        userDao = mock(UserDaoImpl.class);
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        userService = new UserServiceImpl(modelMapper, userDao);
        page = new Page(5, 1);
        pageDto = new PageDto(5, 1);
        user1 = new User(24L, "user@gmail.com");
        userDto1 = new UserDto(24L, "user@gmail.com");
        user2 = new User(1L, "user2@gmail.com");
        userDto2 = new UserDto(1L, "user2@gmail.com");
        user3 = new User(2L, "user3@epam.com");
        userDto3 = new UserDto(2L, "user3@epam.com");
        userDtoInvalid = new UserDto(1L, "user.gmail.com");
    }

    @AfterAll
    static void tearDown() {
        userService = null;
        userDao = null;
        modelMapper = null;
        page = null;
        pageDto = null;
        user1 = null;
        user2 = null;
        user3 = null;
        userDto1 = null;
        userDto2 = null;
        userDto3 = null;
        userDtoInvalid = null;
    }

    @Test
    void addCorrectDataShouldReturnUserDtoId() {
        when(userDao.findByEmail("user@gmail.com")).thenReturn(Optional.empty());
        when(userDao.add(user1)).thenReturn(user1);
        long expected = 24L;
        assertEquals(expected, userService.add(userDto1));
    }

    @Test
    void addIncorrectDataShouldThrowException() {
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userDao.add(any(User.class))).thenReturn(null);
        assertThrows(IncorrectParameterException.class, () -> userService.add(userDtoInvalid));
    }

    @Test
    void findAllCorrectDataShouldReturnUserDtoList() {
        List<User> users = List.of(user2, user3);
        List<UserDto> usersDto = List.of(userDto2, userDto3);

        when(userDao.findAll(page)).thenReturn(users);
        assertEquals(usersDto, userService.findAll(pageDto));
    }

    @Test
    void findByIdCorrectDataShouldReturnUserDto() {
        when(userDao.findById(2L)).thenReturn(Optional.of(user3));
        long id = 2L;
        assertEquals(userDto3, userService.findById(id));
    }

    @Test
    void findByIdCorrectDataShouldThrowException() {
        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        long id = 2L;
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(id));
    }

    @Test
    void findByEmailCorrectDataShouldReturnUserDto() {
        when(userDao.findByEmail("user3@epam.com")).thenReturn(Optional.of(user3));
        long id = 2L;
        assertEquals(userDto3, userService.findByEmail("user3@epam.com"));
    }

    @Test
    void findByEmailCorrectDataShouldThrowException() {
        when(userDao.findByEmail(anyString())).thenReturn(Optional.empty());
        long id = 2L;
        assertThrows(ResourceNotFoundException.class, () -> userService.findByEmail("email@gmail.com"));
    }
}