package com.epam.esm.security.factory;

import com.epam.esm.dto.UserDto;
import com.epam.esm.security.entity.JwtUserInfo;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUserInfo create(Jwt jwt, UserDto user) {
        return new JwtUserInfo(jwt, user.getId(), user.getUsername());
    }
}
