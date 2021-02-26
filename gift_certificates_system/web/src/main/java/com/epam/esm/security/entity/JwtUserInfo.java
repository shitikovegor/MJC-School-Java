package com.epam.esm.security.entity;

import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.Map;

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
     * Constructs a {@code Jwt} using the provided parameters.
     *
     * @param tokenValue the token value
     * @param issuedAt   the time at which the JWT was issued
     * @param expiresAt  the expiration time on or after which the JWT MUST NOT be accepted
     * @param headers    the JOSE header(s)
     * @param claims     the JWT Claims Set
     */
    public JwtUserInfo(String tokenValue, Instant issuedAt, Instant expiresAt, Map<String, Object> headers, Map<String, Object> claims) {
        super(tokenValue, issuedAt, expiresAt, headers, claims);
    }

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
