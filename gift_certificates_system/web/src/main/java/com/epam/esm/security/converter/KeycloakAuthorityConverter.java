package com.epam.esm.security.converter;

import com.nimbusds.jose.shaded.json.JSONArray;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Class {@code KeycloakAuthorityConverter} uses to add user roles to token.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
public class KeycloakAuthorityConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String AUTHORITIES = "authorities";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        JSONArray authorities = (JSONArray) jwt.getClaims().get(AUTHORITIES);
        if (authorities != null) {
            return authorities.stream()
                    .map(String::valueOf)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
        return Collections.emptySet();
    }
}
