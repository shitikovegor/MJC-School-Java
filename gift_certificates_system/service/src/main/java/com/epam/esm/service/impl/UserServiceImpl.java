package com.epam.esm.service.impl;

import com.epam.esm.dao.RoleDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.*;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.Page;
import com.epam.esm.validator.PageValidator;
import com.epam.esm.validator.UserValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String ROLE_USER = "ROLE_USER";

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public long register(UserRegistrationDto userRegistrationDto) {
        UserValidator.validateEmail(userRegistrationDto.getUsername()); // TODO: 14.02.2021 validate all user's fields

        Optional<Role> roleOptional = roleDao.findByName(ROLE_USER);
        RoleDto roleUser = roleOptional.map(role -> modelMapper.map(role, RoleDto.class))
                .orElseThrow(() -> new IncorrectParameterException(ExceptionKey.ROLE_NOT_FOUND, ROLE_USER));

        userRegistrationDto.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        userRegistrationDto.setRoles(List.of(roleUser));
        User user = modelMapper.map(userRegistrationDto, User.class);
        Optional<User> existingUser = userDao.findByUsername(user.getUsername());

        if (existingUser.isEmpty()) {
            User addedUser = userDao.add(user);
            return addedUser.getId();
        } else {
            throw new IncorrectParameterException(ExceptionKey.USER_EXISTS, user.getUsername());
        }
    }

    @Override
    public List<UserDto> findAll(PageDto pageDto) {
        pageDto.setTotalRecords(userDao.findTotalRecords());
        PageValidator.validatePage(pageDto);
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
    public FullUserDto findByUsername(String username) {
        Optional<User> foundUser = userDao.findByUsername(username);
        return foundUser.map(user -> modelMapper.map(user, FullUserDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND,
                        String.valueOf(username)));
    }

    @Transactional
    @Override
    public UserDto authenticateUser(AuthenticationDto authenticationDto) {
        Optional<User> foundUser = userDao.findByUsername(authenticationDto.getUsername());
        User user = foundUser.orElseThrow(() -> new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND,
                String.valueOf(authenticationDto.getUsername())));
        if (!passwordEncoder.matches(authenticationDto.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException(ExceptionKey.USER_NOT_FOUND_BY_LOGIN_OR_PASSWORD);
        }
        return modelMapper.map(user, UserDto.class);
    }
}
