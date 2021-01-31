package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
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

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserDao userDao) {
        this.modelMapper = modelMapper;
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public long add(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        Optional<User> existingUser = userDao.findByEmail(user.getEmail());

        if (existingUser.isEmpty()) {
            User addedUser = userDao.add(user);
            return addedUser.getId();
        } else {
            throw new IncorrectParameterException(ExceptionKey.USER_EXISTS.getKey(), user.getEmail());
        }
    }

    @Override
    public List<UserDto> findAll(PageDto pageDto) {
        Page page = modelMapper.map(pageDto, Page.class);
        return userDao.findAll(page).stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(long id) {
        Optional<User> foundUser = userDao.findById(id);
        return foundUser.map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND.getKey(), String.valueOf(id)));
    }

    // TODO: 31.01.2021 exception, if user exists
    @Transactional
    @Override
    public void remove(long id) {
        Optional<User> userOptional = userDao.findById(id);
        User user = userOptional.orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND.getKey(),
                String.valueOf(id)));
        userDao.remove(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        Optional<User> foundUser = userDao.findByEmail(email);
        return foundUser.map(user -> modelMapper.map(user, UserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND.getKey(),
                        String.valueOf(email)));
    }
}

// TODO: 31.01.2021 validation
