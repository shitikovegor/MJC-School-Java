//package com.epam.esm.controller;
//
//import com.epam.esm.dto.*;
//import com.epam.esm.security.provider.JwtTokenProvider;
//import com.epam.esm.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//
//@RestController
//public class AuthenticationController {
//    private final JwtTokenProvider jwtTokenProvider;
//    private final UserService userService;
//
//    @Autowired
//    public AuthenticationController(JwtTokenProvider jwtTokenProvider, UserService userService) {
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.userService = userService;
//    }
//
//    @PostMapping("/registration")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
//        long userId = userService.register(userRegistrationDto);
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath()
//                .path("users/{id}")
//                .buildAndExpand(userId)
//                .toUri();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(location);
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }
//
//    @PostMapping("/authorization")
//    public AuthenticationResponseDto authenticateUser(@RequestBody AuthenticationDto authenticationDto) {
//        UserDto user = userService.authenticateUser(authenticationDto);
//        String token = jwtTokenProvider.createToken(user.getUsername());
//
//        return new AuthenticationResponseDto(user.getUsername(), token);
//    }
//}
