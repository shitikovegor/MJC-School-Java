package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import com.epam.esm.validator.DtoValidator;
import com.epam.esm.validator.impl.PageValidator;
import com.epam.esm.validator.impl.TagValidator;
import com.epam.esm.validator.impl.UserValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceImplTest {

    private UserService userService;
    private UserDao userDao;
    private ModelMapper modelMapper;
    private Page page;
    private PageDto pageDto;
    private User user;
    private UserDto userDto;
    private DtoValidator<UserRegistrationDto> userValidator;
    private DtoValidator<PageDto> pageValidator;

    @BeforeAll
    void setUp() {
        userDao = mock(UserDaoImpl.class);
        userValidator = new UserValidator();
        pageValidator = new PageValidator();
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(PRIVATE);
        userService = new UserServiceImpl(modelMapper, userDao, userValidator, pageValidator);
        page = new Page(5, 1, 10);
        pageDto = new PageDto(5, 1, 10L);
        user = new User(2L, "user3@epam.com", "", "");
        userDto = new UserDto(2L, "user3@epam.com", "", "");

    }

    @AfterAll
    void tearDown() {
        userService = null;
        userDao = null;
        modelMapper = null;
        page = null;
        pageDto = null;
        user = null;
        userDto = null;
    }

    @Test
    void addCorrectDataShouldReturnUserDtoId() {
        User user1 = new User(24L, "user@gmail.com", "Ivan", "Ivanov");
        UserRegistrationDto userDto1 = new UserRegistrationDto(24L, "user@gmail.com", "User123",
                "User123", "Ivan", "Ivanov");
        when(userDao.findByUsername("user@gmail.com")).thenReturn(Optional.empty());
        when(userDao.add(user1)).thenReturn(user1);
        long expected = 24L;
        assertEquals(expected, userService.register(userDto1));
    }

    @Test
    void addIncorrectDataShouldThrowException() {
        UserRegistrationDto userDtoInvalid = new UserRegistrationDto(1L, "user.gmail.com", "", "", "", "");
        when(userDao.findByUsername(anyString())).thenReturn(Optional.empty());
        when(userDao.add(any(User.class))).thenReturn(null);
        assertThrows(IncorrectParameterException.class, () -> userService.register(userDtoInvalid));
    }

    @Test
    void findAllCorrectDataShouldReturnUserDtoList() {
        User user2 = new User(1L, "user2@gmail.com", "", "");
        UserDto userDto2 = new UserDto(1L, "user2@gmail.com", "", "");
        List<User> users = List.of(user2, user);
        List<UserDto> usersDto = List.of(userDto2, userDto);
        when(userDao.findTotalRecords()).thenReturn(10);
        when(userDao.findAll(page)).thenReturn(users);

        assertEquals(usersDto, userService.findAll(pageDto));
    }

    @Test
    void findByIdCorrectDataShouldReturnUserDto() {
        when(userDao.findById(2L)).thenReturn(Optional.of(user));
        long id = 2L;
        assertEquals(userDto, userService.findById(id));
    }

    @Test
    void findByIdCorrectDataShouldThrowException() {
        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        long id = 2L;
        assertThrows(ResourceNotFoundException.class, () -> userService.findById(id));
    }

    @Test
    void findByEmailCorrectDataShouldReturnUserDto() {
        when(userDao.findByUsername("user3@epam.com")).thenReturn(Optional.of(user));
        long id = 2L;
        assertEquals(userDto, userService.findByUsername("user3@epam.com"));
    }

    @Test
    void findByEmailCorrectDataShouldThrowException() {
        when(userDao.findByUsername(anyString())).thenReturn(Optional.empty());
        long id = 2L;
        assertThrows(ResourceNotFoundException.class, () -> userService.findByUsername("email@gmail.com"));
    }
}