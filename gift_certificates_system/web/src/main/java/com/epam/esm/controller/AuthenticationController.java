package com.epam.esm.controller;

import com.epam.esm.dto.AuthenticationDto;
import com.epam.esm.dto.AuthenticationResponseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.security.provider.JwtTokenProvider;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(name = "/auth")
public class AuthenticationController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationController(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public long registerUser(@RequestBody UserDto userDto) {
        return userService.register(userDto);
    }

    @PostMapping("/login")
    public AuthenticationResponseDto authenticateUser(@RequestBody AuthenticationDto authenticationDto) {
        UserDto userDto = userService.authenticateUser(authenticationDto);
        String token = jwtTokenProvider.createToken(userDto.getUsername(), userDto.getRoles());

        return new AuthenticationResponseDto(userDto.getUsername(), token);
    }
}
