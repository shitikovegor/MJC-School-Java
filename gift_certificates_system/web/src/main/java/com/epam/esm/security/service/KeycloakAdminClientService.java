package com.epam.esm.security.service;

import com.epam.esm.dto.UserRegistrationDto;
import com.epam.esm.exception.ExceptionKey;
import com.epam.esm.security.exception.AuthorizationServerException;
import com.epam.esm.service.UserService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.Collections;

/**
 * Class {@code KeycloakAdminClientService} uses to add users to database and authorization server
 *
 * @author Egor Shitikov
 * @version 1.0
 */
@Service
public class KeycloakAdminClientService {

    private static final int CREATED_STATUS = 201;
    private final Keycloak keycloak;
    private final UserService userService;

    @Value("${keycloak.realm}")
    private String realm;

    @Autowired
    public KeycloakAdminClientService(Keycloak keycloak, UserService userService) {
        this.keycloak = keycloak;
        this.userService = userService;
    }

    @Transactional
    public long addUser(UserRegistrationDto user) {
        long userId = userService.register(user);
        addUserToAuthServer(user);

        return userId;
    }

    private void addUserToAuthServer(UserRegistrationDto user) {
        UsersResource usersResource = keycloak.realm(realm).users();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEmail(user.getUsername());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        CredentialRepresentation passwordCredential = createPasswordCredentials(user.getPassword());
        userRepresentation.setCredentials(Collections.singletonList(passwordCredential));

        try (Response response = usersResource.create(userRepresentation)) {
            if (response.getStatus() != CREATED_STATUS) {
                throw new AuthorizationServerException(ExceptionKey.AUTH_SERVER_ERROR,
                        response.getStatusInfo() + " (" + response.getStatus() + ")");
            }
        }
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }
}
