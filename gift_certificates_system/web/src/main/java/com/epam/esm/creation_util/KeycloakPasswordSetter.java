package com.epam.esm.creation_util;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.UserService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
public class KeycloakPasswordSetter {
    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;
    private final UserService userService;

    @Autowired
    public KeycloakPasswordSetter(Keycloak keycloak, UserService userService) {
        this.keycloak = keycloak;
        this.userService = userService;
    }

    public Map<String, String> resetUsersPasswords() {
        Map<String, String> userPasswords = new HashMap<>();
        List<UserDto> users = userService.findAll(new PageDto(1000, 1));
        UsersResource usersResource = keycloak.realm(realm).users();

        users.forEach(user -> {
            String userId = usersResource.search(user.getUsername()).get(0).getId();
            UserResource userResource = usersResource.get(userId);
            String password = UserUpdater.generatePassword();
            userResource.resetPassword(createPasswordCredentials(password));
            userPasswords.put(user.getUsername(), password);
        });
        return userPasswords;
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
