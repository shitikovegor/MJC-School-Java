package com.epam.esm.security.provider;

import com.epam.esm.dto.RoleDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    private static final int TOKEN_BEARER_LENGTH = 7;
    private final UserDetailsService userDetailsService;
    @Value(value = "${jwt.token.secret}")
    private String secret;
    @Value(value = "${jwt.token.expired}")
    private long expirationTime;

    @Autowired
    public JwtTokenProvider(@Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String createToken(String username) {
        Claims claims = Jwts.claims().setSubject(username);

        Date now = new Date();
        Date validity = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Optional<String> resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer_")) {
            return Optional.of(bearerToken.substring(TOKEN_BEARER_LENGTH));
        } else {
            return Optional.empty();
        }
    }

    public boolean isTokenValid(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }
}
