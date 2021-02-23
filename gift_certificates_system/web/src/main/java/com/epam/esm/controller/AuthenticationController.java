package com.epam.esm.controller;

import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.security.service.KeycloakAdminClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

/**
 * Class {@code AuthenticationController} uses to work with authentication information.
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@RestController
public class AuthenticationController {
    private final KeycloakAdminClientService keycloakService;

    /**
     * Instantiates a new Authentication controller.
     *
     * @param keycloakService the keycloak admin client service
     */
    @Autowired
    public AuthenticationController(KeycloakAdminClientService keycloakService) {
        this.keycloakService = keycloakService;
    }

    /**
     * Register user response entity.
     *
     * @param userRegistrationDto the user registration dto
     * @return the response entity
     */
    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        keycloakService.addUser(userRegistrationDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("users/{id}")
                .buildAndExpand("userId")
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
