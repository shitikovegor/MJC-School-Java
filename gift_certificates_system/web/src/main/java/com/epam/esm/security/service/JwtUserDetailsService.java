package com.epam.esm.security.service;

import com.epam.esm.dto.FullUserDto;
import com.epam.esm.security.entity.JwtUser;
import com.epam.esm.security.factory.JwtUserFactory;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FullUserDto user = userService.findByUsername(username);

        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
