package com.epam.esm.security.factory;

import com.epam.esm.dto.FullUserDto;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.security.entity.JwtUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(FullUserDto user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                /*user.getPassword(),*/
                mapToGrantedAuthorities(user.getRoles()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<RoleDto> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
