package com.epam.esm.security.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.security.entity.JwtUser;
import com.epam.esm.security.factory.JwtUserFactory;
import com.epam.esm.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public JwtUserDetailsService(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = userService.findByUsername(username);

        JwtUser jwtUser = JwtUserFactory.create(modelMapper.map(user, User.class));
        return jwtUser;
    }
}
