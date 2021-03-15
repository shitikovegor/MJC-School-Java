package com.epam.esm.security.entity;

import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

/**
 * Class {@code JwtUserInfo} represents user info for token
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class JwtUserInfo extends Jwt {

    private long userId;
    private String username;

    /**
     * Instantiates a new Jwt user info.
     *
     * @param jwt      the jwt
     * @param userId   the user id
     * @param username the username
     */
    public JwtUserInfo(Jwt jwt, long userId, String username) {
        super(jwt.getTokenValue(), jwt.getIssuedAt(), jwt.getExpiresAt(), jwt.getHeaders(), jwt.getClaims());
        this.userId = userId;
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
