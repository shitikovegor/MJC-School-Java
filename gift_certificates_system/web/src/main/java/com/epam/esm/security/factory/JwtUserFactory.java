package com.epam.esm.security.factory;

import com.epam.esm.dto.UserDto;
import com.epam.esm.security.entity.JwtUserInfo;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * Class {@code JwtUserFactory} uses to generate {@link JwtUserInfo} object
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@Component
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    /**
     * Create jwt user info.
     *
     * @param jwt  the jwt
     * @param user the user
     * @return the jwt user info
     */
    public static JwtUserInfo create(Jwt jwt, UserDto user) {
        return new JwtUserInfo(jwt, user.getId(), user.getUsername());
    }
}
