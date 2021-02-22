package com.epam.esm.security.converter;

import com.epam.esm.security.entity.JwtUserInfo;
import com.epam.esm.security.service.JwtUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.util.Assert;

import java.util.Collection;

public class JwtUserInfoConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private Converter<Jwt, Collection<GrantedAuthority>> grantedAuthoritiesConverter;
    @Autowired
    private JwtUserInfoService service;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> authorities = grantedAuthoritiesConverter.convert(jwt);
        JwtUserInfo jwtUserInfo = service.loadJwtUserInfoByJwt(jwt);
        return new JwtAuthenticationToken(jwtUserInfo, authorities);
    }

    public void setGrantedAuthoritiesConverter(
            Converter<Jwt, Collection<GrantedAuthority>> grantedAuthoritiesConverter) {
        Assert.notNull(grantedAuthoritiesConverter, "jwtGrantedAuthoritiesConverter cannot be null");
        this.grantedAuthoritiesConverter = grantedAuthoritiesConverter;
    }
}
