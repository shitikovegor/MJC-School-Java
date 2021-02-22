package com.epam.esm.security.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.security.entity.JwtUserInfo;
import com.epam.esm.security.factory.JwtUserFactory;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.USERNAME;

@Service
public class JwtUserInfoService {
    private final UserService userService;

    @Autowired
    public JwtUserInfoService(UserService userService) {
        this.userService = userService;
    }

    public JwtUserInfo loadJwtUserInfoByJwt(Jwt jwt) {
        UserDto user = userService.findByUsername(jwt.getClaimAsString(USERNAME));
        JwtUserInfo jwtUserInfo = JwtUserFactory.create(jwt, user);
        return jwtUserInfo;
    }
}
