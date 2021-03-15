package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.ResourceNotFoundException;
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
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserDao userDao;
    private final DtoValidator<UserRegistrationDto> userValidator;
    private final DtoValidator<PageDto> pageValidator;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserDao userDao, DtoValidator<UserRegistrationDto> userValidator, DtoValidator<PageDto> pageValidator) {
        this.modelMapper = modelMapper;
        this.userDao = userDao;
        this.userValidator = userValidator;
        this.pageValidator = pageValidator;
    }

    @Transactional
    @Override
    public long register(UserRegistrationDto userRegistrationDto) {
        userValidator.validate(userRegistrationDto);
        User user = modelMapper.map(userRegistrationDto, User.class);
        Optional<User> existingUser = userDao.findByUsername(user.getUsername());

        if (!existingUser.isPresent()) {
            User addedUser = userDao.add(user);
            return addedUser.getId();
        } else {
            throw new IncorrectParameterException(ExceptionKey.USER_EXISTS, user.getUsername());
        }
    }

    @Override
    public List<UserDto> findAll(PageDto pageDto) {
        pageValidator.validate(pageDto);
        Page page = modelMapper.map(pageDto, Page.class);
        return userDao.findAll(page).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(long id) {
        Optional<User> foundUser = userDao.findById(id);
        return foundUser.map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND, String.valueOf(id)));
    }

    @Override
    public UserDto findByUsername(String username) {
        Optional<User> foundUser = userDao.findByUsername(username);
        return foundUser.map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND,
                        String.valueOf(username)));
    }

    @Transactional
    @Override
    public UserDto update(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return modelMapper.map(userDao.update(user), UserDto.class);
    }

    @Override
    public long findTotalRecords() {
        return userDao.findTotalRecords();
    }
}
